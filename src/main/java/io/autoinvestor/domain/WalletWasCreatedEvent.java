package io.autoinvestor.domain;

import java.util.Date;

public class WalletWasCreatedEvent extends Event<WalletWasCreatedPayload>{

    private WalletWasCreatedEvent(Id aggregateId, WalletWasCreatedPayload payload) {
        super(aggregateId, "WALLET_CREATED", payload);
    }

    public static WalletWasCreatedEvent with(
            WalletId holdingId,
            UserId userId
    ) {
        var payload = new WalletWasCreatedPayload(userId);
        return new WalletWasCreatedEvent(holdingId, payload);
    }

    protected WalletWasCreatedEvent(WalletId walletId, WalletWasCreatedPayload payload, int version, Date occurredAt) {
        super(walletId, "WALLET_CREATED", payload, version, occurredAt);
    }

    public static WalletWasCreatedEvent hydrate(WalletId walletId,
                                                WalletWasCreatedPayload payload,
                                                int version,
                                                Date occurredAt) {
        return new WalletWasCreatedEvent(walletId, payload, version, occurredAt);
    }

}
