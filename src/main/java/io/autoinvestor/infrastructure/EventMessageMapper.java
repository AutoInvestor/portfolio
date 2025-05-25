package io.autoinvestor.infrastructure;

import io.autoinvestor.domain.Event;
import io.autoinvestor.domain.HoldingWasCreatedEventPayload;
import io.autoinvestor.domain.HoldingWasUpdatedEventPayload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventMessageMapper {

    public List<HoldingAddedOrUpdatedMessage> mapToHoldingAddedMessage(List<Event<?>> holdingAddedEvent) {
        List<HoldingAddedOrUpdatedMessage> holdingAddedMessages = new ArrayList<>();
        for (Event<?> holdingWasAdded : holdingAddedEvent) {
            HoldingWasCreatedEventPayload payload = (HoldingWasCreatedEventPayload) holdingWasAdded.getPayload();
            HoldingAddedMessagePayload holdingAddedMessagePayload = new HoldingAddedMessagePayload(
                    payload.userId().value(),
                    payload.assetId().value(),
                    payload.amount().value(),
                    payload.boughtPrice().value()
            );
            HoldingAddedOrUpdatedMessage holdingAddedMessage = new HoldingAddedOrUpdatedMessage(
                    holdingWasAdded.getId().value(),
                    holdingWasAdded.getOccurredAt(),
                    holdingWasAdded.getAggregateId().value(),
                    holdingWasAdded.getVersion(),
                    holdingWasAdded.getType(),
                    holdingAddedMessagePayload
            );
            holdingAddedMessages.add(holdingAddedMessage);
        }
        return holdingAddedMessages;
    }

    public List<HoldingAddedOrUpdatedMessage> mapToHoldingUpdatedMessage(List<Event<?>> holdingAddedEvent) {
        List<HoldingAddedOrUpdatedMessage> holdingAddedMessages = new ArrayList<>();
        for (Event<?> holdingWasAdded : holdingAddedEvent) {
            HoldingWasUpdatedEventPayload payload = (HoldingWasUpdatedEventPayload) holdingWasAdded.getPayload();
            HoldingAddedMessagePayload holdingAddedMessagePayload = new HoldingAddedMessagePayload(
                    payload.userId().value(),
                    payload.assetId().value(),
                    payload.amount().value(),
                    payload.boughtPrice().value()
            );
            HoldingAddedOrUpdatedMessage holdingAddedMessage = new HoldingAddedOrUpdatedMessage(
                    holdingWasAdded.getId().value(),
                    holdingWasAdded.getOccurredAt(),
                    holdingWasAdded.getAggregateId().value(),
                    holdingWasAdded.getVersion(),
                    holdingWasAdded.getType(),
                    holdingAddedMessagePayload
            );
            holdingAddedMessages.add(holdingAddedMessage);
        }
        return holdingAddedMessages;
    }
}
