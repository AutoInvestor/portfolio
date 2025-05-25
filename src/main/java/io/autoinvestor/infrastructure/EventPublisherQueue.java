package io.autoinvestor.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventPublisherQueue {

    private static final String TOPIC = "portfolio";
    private final PubSubTemplate pubSubTemplate;

    public void publishHoldingAddedOrUpdated(List<HoldingAddedOrUpdatedMessage> holdingAddedMessages) {
        ObjectMapper objectMapper = new ObjectMapper();
        for (HoldingAddedOrUpdatedMessage message : holdingAddedMessages) {
            try {
                String jsonMessage = objectMapper.writeValueAsString(message);
                pubSubTemplate.publish(TOPIC, jsonMessage);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error serializing message to JSON", e);
            }
        }

    }
    public void publishHoldingDeleted(List<HoldingDeletedMessage> holdingDeletedMessages) {
        ObjectMapper objectMapper = new ObjectMapper();
        for (HoldingDeletedMessage message : holdingDeletedMessages) {
            try {
                String jsonMessage = objectMapper.writeValueAsString(message);
                pubSubTemplate.publish(TOPIC, jsonMessage);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error serializing message to JSON", e);
            }
        }

    }
}
