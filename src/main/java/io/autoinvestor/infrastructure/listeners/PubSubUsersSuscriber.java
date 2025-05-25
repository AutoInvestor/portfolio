package io.autoinvestor.infrastructure.listeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiService;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import io.autoinvestor.application.WalletCreatedUseCase.WalletCreateCommand;
import io.autoinvestor.application.WalletCreatedUseCase.WalletCreatedHandler;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.cloud.pubsub.v1.Subscriber;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component

public class PubSubUsersSuscriber {
    private final WalletCreatedHandler commandHandler;
    private final ProjectSubscriptionName subscriptionName;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PubSubEventMapper eventMapper;

    private Subscriber subscriber;

    public PubSubUsersSuscriber (WalletCreatedHandler walletCreatedHandler,
                                 PubSubEventMapper mapper,
            @Value("${spring.cloud.gcp.pubsub.project-id}") String projectId,
            @Value("${PUBSUB_SUBSCRIPTION_PORTFOLIO}") String subscriptionId)
    {
        this.commandHandler = walletCreatedHandler;
        this.eventMapper = mapper;
        this.subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);

    }

    @PostConstruct
    public void listen () {
        MessageReceiver receiver = this::processMessage;

        this.subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        this.subscriber.addListener(new ApiService.Listener() {
            @Override public void failed(ApiService.State from, Throwable failure) {}
        }, Runnable::run);
        subscriber.startAsync().awaitRunning();
    }

    @PreDestroy
    public void stop() {
        if (subscriber != null) {
            subscriber.stopAsync();
        }
    }

    private void processMessage(PubsubMessage message, AckReplyConsumer consumer) {
        try {
            Map<String, Object> raw = this.objectMapper.readValue(
                    message.getData().toByteArray(), new TypeReference<Map<String, Object>>() {
                    }
            );
            PubSubEvent event = this.eventMapper.fromMap(raw);
            if("USER_CREATED".equals(event.getType())) {
                WalletCreateCommand command = new WalletCreateCommand(
                        (String) event.getAggregateId()
                );
                this.commandHandler.handle(command);
            }


        } catch (Exception ex) {
            consumer.nack();
        }
    }

}
