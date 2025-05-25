package io.autoinvestor.domain;

import java.util.Date;

public class HoldingWasDeletedEvent extends Event<HoldingWasDeletedEventPayload> {
    protected HoldingWasDeletedEvent(Id aggregateId, HoldingWasDeletedEventPayload payload) {
        super(aggregateId, "PORTFOLIO_ASSET_REMOVED", payload);
    }

    protected HoldingWasDeletedEvent(WalletId walletId, HoldingWasDeletedEventPayload payload, int version, Date occurredAt) {
        super(walletId, "PORTFOLIO_ASSET_REMOVED", payload, version, occurredAt);
    }

    public static HoldingWasDeletedEvent with(
            WalletId walletId,
            AssetId assetId,
    ) {
        var payload = new HoldingWasDeletedEventPayload(assetId);
        return new HoldingWasDeletedEvent(walletId, payload);
    }

    public static HoldingWasDeletedEvent hydrate (
            WalletId walletId,
            HoldingWasDeletedEventPayload payload,
            int version,
            Date occurredAt
    ) {
        return new HoldingWasDeletedEvent(walletId, payload, version, occurredAt);
    }
}
