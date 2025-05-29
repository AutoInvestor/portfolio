package io.autoinvestor.domain.events;

import io.autoinvestor.domain.Id;
import io.autoinvestor.domain.model.Amount;
import io.autoinvestor.domain.model.AssetId;
import io.autoinvestor.domain.model.UserId;
import io.autoinvestor.domain.model.WalletId;

import java.util.Date;

public class HoldingWasDeletedEvent extends Event<HoldingWasDeletedEventPayload> {
    public static final String TYPE = "PORTFOLIO_ASSET_REMOVED";

    private HoldingWasDeletedEvent(Id aggregateId, HoldingWasDeletedEventPayload payload) {
        super (aggregateId, TYPE, payload);
    }

    protected HoldingWasDeletedEvent(
            EventId id,
            Id aggregateId,
            HoldingWasDeletedEventPayload payload,
            Date occurredAt,
            int version) {
        super(id, aggregateId, TYPE, payload, occurredAt, version);
    }

    public static HoldingWasDeletedEvent with(WalletId walletId,
                                              UserId userId,
                                              AssetId assetId) {
        HoldingWasDeletedEventPayload payload = new HoldingWasDeletedEventPayload(
                userId.value(), assetId.value()
        );
        return new HoldingWasDeletedEvent(walletId, payload);
    }

    public static HoldingWasDeletedEvent hydrate (EventId id,
                                                  Id aggregateId,
                                                  HoldingWasDeletedEventPayload payload,
                                                  Date occurredAt,
                                                  int version) {
        return new HoldingWasDeletedEvent(id, aggregateId, payload, occurredAt, version);
    }

}
