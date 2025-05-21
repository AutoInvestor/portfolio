package io.autoinvestor.infrastructure;

import io.autoinvestor.application.HoldingReadModel;
import io.autoinvestor.domain.HoldingWasAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HoldingProjection {
    private final HoldingReadModel holdingReadModel;

    @EventListener
    public void onHoldingAdded(HoldingWasAddedEvent holdingWasAddedEvent) {
        String holdingId = holdingWasAddedEvent.getAggregateId().value();
        String userId = holdingWasAddedEvent.getPayload().userId().value();
        String assetId = holdingWasAddedEvent.getPayload().assetId().value();
        Integer amount = holdingWasAddedEvent.getPayload().amount().value();
        Integer boughtPrice = holdingWasAddedEvent.getPayload().boughtPrice().value();

        HoldingReadModelDocument holdingReadModelDocument = new HoldingReadModelDocument(
                holdingId, userId, assetId, amount, boughtPrice
        );
        this.holdingReadModel.add(holdingReadModelDocument);
    }
}
