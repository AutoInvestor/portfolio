package io.autoinvestor.domain.events;

import io.autoinvestor.domain.*;
import io.autoinvestor.domain.model.*;

import java.util.Date;

public class HoldingWasUpdatedEvent extends Event<HoldingWasUpdatedEventPayload> {
    public static final String TYPE = "PORTFOLIO_ASSET_UPDATED";

    private HoldingWasUpdatedEvent(Id aggregateId, HoldingWasUpdatedEventPayload payload) {
        super(aggregateId, TYPE, payload);
    }

    protected HoldingWasUpdatedEvent(
            EventId id,
            Id aggregateId,
            HoldingWasUpdatedEventPayload payload,
            Date occurredAt,
            int version) {
        super(id, aggregateId, TYPE, payload, occurredAt, version);
    }

    public static HoldingWasUpdatedEvent with(
            WalletId walletId,
            UserId userId,
            AssetId assetId,
            Amount amount,
            BoughtPrice boughtPrice) {
        HoldingWasUpdatedEventPayload payload =
                new HoldingWasUpdatedEventPayload(
                        userId.value(), assetId.value(), amount.value(), boughtPrice.value());
        return new HoldingWasUpdatedEvent(walletId, payload);
    }

    public static HoldingWasUpdatedEvent hydrate(
            EventId id,
            Id aggregateId,
            HoldingWasUpdatedEventPayload payload,
            Date occurredAt,
            int version) {
        return new HoldingWasUpdatedEvent(id, aggregateId, payload, occurredAt, version);
    }
}
