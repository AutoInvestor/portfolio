package io.autoinvestor.domain;

public class HoldingWasAddedEvent extends Event<HoldingWasAddedEventPayload>{

    private HoldingWasAddedEvent(Id aggregateId, HoldingWasAddedEventPayload payload) {
        super(aggregateId, "PORTFOLIO_HOLDING_ADDED", payload);
    }

    public static HoldingWasAddedEvent with(
            HoldingId holdingId,
            UserId userId,
            AssetId assetId,
            Amount amount,
            BoughtPrice boughtPrice
    ) {
        var payload = new HoldingWasAddedEventPayload(userId, assetId, amount, boughtPrice);
        return new HoldingWasAddedEvent(holdingId, payload);
    }

}
