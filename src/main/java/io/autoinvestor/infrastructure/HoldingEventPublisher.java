package io.autoinvestor.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HoldingEventPublisher {

    private static final String TOPIC = "portfolio";
    private final PubSubTemplate pubSubTemplate;

    public void publishHoldingAdded(List<HoldingAddedMessage> holdingAddedMessages) {
        ObjectMapper objectMapper = new ObjectMapper();
        for (HoldingAddedMessage message : holdingAddedMessages) {
            try {
                String jsonMessage = objectMapper.writeValueAsString(holdingAddedMessages);
                pubSubTemplate.publish(TOPIC, jsonMessage);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error serializing message to JSON", e);
            }
        }

    }
}
