package io.autoinvestor.infrastructure;

import io.autoinvestor.domain.Event;
import io.autoinvestor.domain.HoldingWasAddedEventPayload;
import org.springframework.stereotype.Component;

@Component
public class AddHoldingEventMapperDocument {
    HoldingAddedEventDocument toDocument(Event<?> holdingAddedEvent){
        HoldingWasAddedEventPayload payload = (HoldingWasAddedEventPayload) holdingAddedEvent.getPayload();
        return new HoldingAddedEventDocument(
                holdingAddedEvent.getId().value(),
                holdingAddedEvent.getAggregateId().value(),
                payload.userId().value(),
                payload.assetId().value(),
                payload.amount().value(),
                payload.boughtPrice().value(),
                holdingAddedEvent.getOccurredAt().toInstant(),
                holdingAddedEvent.getVersion()
        );
    }
}
