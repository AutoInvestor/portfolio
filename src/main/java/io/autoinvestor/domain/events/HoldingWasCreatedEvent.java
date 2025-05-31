package io.autoinvestor.domain.events;

import io.autoinvestor.domain.*;
import io.autoinvestor.domain.model.*;

import java.util.Date;

public class HoldingWasCreatedEvent extends Event<HoldingWasCreatedEventPayload> {
    public static final String TYPE = "PORTFOLIO_ASSET_ADDED";

    private HoldingWasCreatedEvent(Id aggregateId, HoldingWasCreatedEventPayload payload) {
        super(aggregateId, TYPE, payload);
    }

    protected HoldingWasCreatedEvent(
            EventId id,
            Id aggregateId,
            HoldingWasCreatedEventPayload payload,
            Date occurredAt,
            int version) {
        super(id, aggregateId, TYPE, payload, occurredAt, version);
    }

    public static HoldingWasCreatedEvent with(
            WalletId walletId,
            UserId userId,
            AssetId assetId,
            Amount amount,
            BoughtPrice boughtPrice) {
        HoldingWasCreatedEventPayload payload =
                new HoldingWasCreatedEventPayload(
                        userId.value(), assetId.value(), amount.value(), boughtPrice.value());
        return new HoldingWasCreatedEvent(walletId, payload);
    }

    public static HoldingWasCreatedEvent hydrate(
            EventId id,
            Id aggregateId,
            HoldingWasCreatedEventPayload payload,
            Date occurredAt,
            int version) {
        return new HoldingWasCreatedEvent(id, aggregateId, payload, occurredAt, version);
    }
}
