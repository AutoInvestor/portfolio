package io.autoinvestor.infrastructure.listeners;

import io.autoinvestor.application.WalletCreatedUseCase.WalletCreateCommand;
import io.autoinvestor.application.WalletCreatedUseCase.WalletCreatedHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiService;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

@Slf4j
@Component
@Profile("prod")
public class PubSubUsersSubscriber {
    private final WalletCreatedHandler commandHandler;
    private final ProjectSubscriptionName subscriptionName;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PubSubEventMapper eventMapper;

    private Subscriber subscriber;

    public PubSubUsersSubscriber(
            WalletCreatedHandler walletCreatedHandler,
            PubSubEventMapper mapper,
            @Value("${GCP_PROJECT}") String projectId,
            @Value("${PUBSUB_SUBSCRIPTION_USERS}") String subscriptionId) {
        this.commandHandler = walletCreatedHandler;
        this.eventMapper = mapper;
        this.subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
    }

    @PostConstruct
    public void listen() {
        log.info("Starting Pub/Sub subscriber for {}", subscriptionName);

        MessageReceiver receiver = this::processMessage;

        this.subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        this.subscriber.addListener(
                new ApiService.Listener() {
                    @Override
                    public void failed(ApiService.State from, Throwable failure) {
                        log.error(
                                "Subscriber failed from state {}: {}",
                                from,
                                failure.toString(),
                                failure); // ERROR
                    }
                },
                Runnable::run);
        this.subscriber.startAsync().awaitRunning();
        log.info("Subscriber running");
    }

    @PreDestroy
    public void stop() {
        if (this.subscriber != null) {
            log.info("Stopping subscriber...");
            this.subscriber.stopAsync();
        }
    }

    private void processMessage(PubsubMessage message, AckReplyConsumer consumer) {
        String msgId = message.getMessageId();
        log.debug("Received message msgId={} size={}B", msgId, message.getData().size());

        try {
            Map<String, Object> raw =
                    objectMapper.readValue(
                            message.getData().toByteArray(), new TypeReference<>() {});
            PubSubEvent event = eventMapper.fromMap(raw);
            log.info("Processing event type={} msgId={}", event.getType(), msgId);

            if ("USER_CREATED".equals(event.getType())) {
                if (event.getAggregateId() == null) {
                    log.warn(
                            "Malformed event: Skipping USER_CREATED event with missing aggregateId msgId={}",
                            msgId);
                    consumer.ack();
                    return;
                }

                WalletCreateCommand command = new WalletCreateCommand(event.getAggregateId());
                this.commandHandler.handle(command);
                log.info("User registered for userId={} msgId={}", command.userId(), msgId);
            } else {
                log.debug("Ignored unsupported event type={} msgId={}", event.getType(), msgId);
            }

            consumer.ack();
        } catch (Exception ex) {
            log.error("Failed to handle msgId={} — nacking", msgId, ex);
            consumer.nack();
        }
    }
}
