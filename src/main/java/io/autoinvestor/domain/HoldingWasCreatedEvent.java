package io.autoinvestor.domain;

import java.util.Date;

public class HoldingWasCreatedEvent extends Event<HoldingWasCreatedEventPayload>{
    protected HoldingWasCreatedEvent(Id aggregateId, HoldingWasCreatedEventPayload payload) {
        super(aggregateId, "PORTFOLIO_ASSET_ADDED", payload);
    }

    protected HoldingWasCreatedEvent(WalletId walletId, HoldingWasCreatedEventPayload payload, int version, Date occurredAt) {
        super(walletId, "PORTFOLIO_ASSET_ADDED", payload, version, occurredAt);
    }
    public static HoldingWasCreatedEvent with(
            WalletId walletId,
            UserId userId,
            AssetId assetId,
            Amount amount,
            BoughtPrice boughtPrice
    ) {
        var payload = new HoldingWasCreatedEventPayload(userId, assetId, amount, boughtPrice);
        return new HoldingWasCreatedEvent(walletId, payload);
    }

    public static HoldingWasCreatedEvent hydrate(
            WalletId walletId,
            HoldingWasCreatedEventPayload payload,
            int version,
            Date occurredAt
    ) {
        return new HoldingWasCreatedEvent(walletId, payload, version, occurredAt);
    }

}
