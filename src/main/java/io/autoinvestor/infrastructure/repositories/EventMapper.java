package io.autoinvestor.infrastructure.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.autoinvestor.domain.events.*;
import io.autoinvestor.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class EventMapper {

    private final ObjectMapper json = new ObjectMapper();

    public <P extends EventPayload> EventDocument toDocument(Event<P> evt) {
        Map<String, Object> payloadMap =
                json.convertValue(evt.getPayload(), new TypeReference<Map<String, Object>>() {});

        return new EventDocument(
                evt.getId().value(),
                evt.getAggregateId().value(),
                evt.getType(),
                payloadMap,
                evt.getOccurredAt(),
                evt.getVersion()
        );
    }

    public Event<?> toDomain(EventDocument doc) {
        EventId id = EventId.of(doc.getId());
        WalletId aggId = WalletId.of(doc.getAggregateId());
        Date occurred = doc.getOccurredAt();
        int version = doc.getVersion();

        switch (doc.getType()) {
            case WalletWasCreatedEvent.TYPE -> {
                WalletWasCreatedEventPayload payload =
                        json.convertValue(doc.getPayload(), WalletWasCreatedEventPayload.class);

                return WalletWasCreatedEvent.hydrate(id, aggId, payload, occurred, version);
            }
            case HoldingWasCreatedEvent.TYPE -> {
                HoldingWasCreatedEventPayload payload =
                        json.convertValue(doc.getPayload(), HoldingWasCreatedEventPayload.class);

                return HoldingWasCreatedEvent.hydrate(id, aggId, payload, occurred, version);
            }
            case HoldingWasUpdatedEvent.TYPE -> {
                HoldingWasUpdatedEventPayload payload =
                        json.convertValue(doc.getPayload(), HoldingWasUpdatedEventPayload.class);

                return HoldingWasUpdatedEvent.hydrate(id, aggId, payload, occurred, version);
            }

            case HoldingWasDeletedEvent.TYPE -> {
                HoldingWasDeletedEventPayload payload =
                        json.convertValue(doc.getPayload(), HoldingWasDeletedEventPayload.class);
                return HoldingWasDeletedEvent.hydrate(id, aggId, payload, occurred, version);
            }
            default -> throw new IllegalArgumentException("Unknown event type: " + doc.getType()
            );
        }
    }
}
