package io.autoinvestor.domain.events;

import io.autoinvestor.domain.Id;
import io.autoinvestor.domain.model.*;

import java.util.Date;

public class WalletWasCreatedEvent extends Event<WalletWasCreatedEventPayload> {
    public static final String TYPE = "WALLET_CREATED";

    private WalletWasCreatedEvent(Id aggregateId, WalletWasCreatedEventPayload payload) {
        super(aggregateId, TYPE, payload);
    }

    protected WalletWasCreatedEvent(EventId id,
                                    Id aggregateId,
                                    WalletWasCreatedEventPayload payload,
                                    Date occurredAt,
                                    int version) {
        super(id, aggregateId, TYPE, payload, occurredAt, version);
    }

    public static WalletWasCreatedEvent with(WalletId walletId,
                                             UserId userId) {
        WalletWasCreatedEventPayload payload = new WalletWasCreatedEventPayload(
                userId.value()
        );
        return new WalletWasCreatedEvent(walletId, payload);
    }

    public static WalletWasCreatedEvent hydrate(EventId id,
                                                Id aggregateId,
                                                WalletWasCreatedEventPayload payload,
                                                Date occurredAt,
                                                int version) {
        return new WalletWasCreatedEvent(id, aggregateId, payload, occurredAt, version);
    }

}
