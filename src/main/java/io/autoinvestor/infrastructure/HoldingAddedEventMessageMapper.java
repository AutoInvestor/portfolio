package io.autoinvestor.infrastructure;

import io.autoinvestor.domain.Event;
import io.autoinvestor.domain.HoldingWasAddedEventPayload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class HoldingAddedEventMessageMapper {

    public List<HoldingAddedMessage> map(List<Event<?>> holdingAddedEvent) {
        List<HoldingAddedMessage> holdingAddedMessages = new ArrayList<>();
        for (Event<?> holdingWasAdded : holdingAddedEvent) {
            HoldingWasAddedEventPayload payload = (HoldingWasAddedEventPayload) holdingWasAdded.getPayload();
            HoldingAddedMessagePayload holdingAddedMessagePayload = new HoldingAddedMessagePayload(
                    payload.userId().value(),
                    payload.assetId().value(),
                    payload.amount().value(),
                    payload.boughtPrice().value()
            );
            HoldingAddedMessage holdingAddedMessage = new HoldingAddedMessage(
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
