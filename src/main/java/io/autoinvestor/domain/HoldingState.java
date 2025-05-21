package io.autoinvestor.domain;

public record HoldingState(
    HoldingId holdingId,
    UserId userId,
    AssetId assetId,
    Amount amount,
    BoughtPrice boughtPrice
) {
    public static HoldingState withHoldingAdded(HoldingWasAddedEvent event) {
        HoldingWasAddedEventPayload payload = event.getPayload();
        return new HoldingState(
                (HoldingId) event.getAggregateId(),
                payload.userId(),
                payload.assetId(),
                payload.amount(),
                payload.boughtPrice());
    }
}
