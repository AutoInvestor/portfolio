package io.autoinvestor.domain;

import java.util.Date;

public class HoldingWasUpdatedEvent extends Event<HoldingWasUpdatedEventPayload>{
    protected HoldingWasUpdatedEvent(Id aggregateId, HoldingWasUpdatedEventPayload payload) {
        super(aggregateId, "PORTFOLIO_ASSET_UPDATED", payload);
    }

    protected HoldingWasUpdatedEvent(WalletId walletId, HoldingWasUpdatedEventPayload payload, int version, Date occurredAt) {
        super(walletId, "PORTFOLIO_ASSET_UPDATED", payload, version, occurredAt);
    }

    public static HoldingWasUpdatedEvent with(
         WalletId walletId,
         UserId userId,
         AssetId assetId,
         Amount amount,
         BoughtPrice boughtPrice
    ) {
        var payload = new HoldingWasUpdatedEventPayload(userId, assetId, amount, boughtPrice);
        return new HoldingWasUpdatedEvent(walletId, payload);
    }

    public static HoldingWasUpdatedEvent hydrate(
            WalletId walletId,
            HoldingWasUpdatedEventPayload payload,
            int version,
            Date occurredAt
    ) {
        return new HoldingWasUpdatedEvent(walletId, payload, version, occurredAt);
    }

}
