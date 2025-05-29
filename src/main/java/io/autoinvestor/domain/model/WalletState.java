package io.autoinvestor.domain.model;


import io.autoinvestor.domain.events.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class WalletState {
    private final WalletId walletId;
    private final UserId userId;
    private final Map<AssetId, Holding> holdings;

    private WalletState(WalletId walletId, UserId userId, Map<AssetId, Holding> holdings) {
        this.walletId = walletId;
        this.userId = userId;
        this.holdings = holdings;
    }

    public static WalletState empty() {
        return new WalletState(WalletId.generate(), UserId.empty(), new HashMap<>());
    }

    public WalletState withWalletCreated(WalletWasCreatedEvent event) {
        WalletWasCreatedEventPayload payload = event.getPayload();
        return new WalletState(
                WalletId.of(event.getAggregateId().value()),
                UserId.of(payload.userId()),
                new HashMap<>()
        );
    }

    public WalletState withHoldingCreated(HoldingWasCreatedEvent event) {
        HoldingWasCreatedEventPayload payload = event.getPayload();
        WalletId walletId = (WalletId) event.getAggregateId();
        UserId userId = UserId.of(payload.userId());
        Holding newHolding = Holding.of(Amount.of(payload.amount()), BoughtPrice.of(payload.boughtPrice()));
        Map<AssetId, Holding> holdingsUpdated =  new HashMap<>(this.holdings);
        holdingsUpdated.put(AssetId.of(payload.assetId()), newHolding);
        return new WalletState(
                walletId,
                userId,
                holdingsUpdated
        );
    }

    public WalletState withHoldingUpdated(HoldingWasUpdatedEvent event) {
        HoldingWasUpdatedEventPayload payload = event.getPayload();
        Holding holdingUpdated = Holding.of(Amount.of(payload.amount()), BoughtPrice.of(payload.boughtPrice()));
        holdings.put(AssetId.of(payload.assetId()), holdingUpdated);
        return new WalletState(
                this.walletId,
                this.userId,
                this.holdings
        );
    }

    public WalletState withHoldingDeleted(HoldingWasDeletedEvent event) {
        HoldingWasDeletedEventPayload payload = event.getPayload();
        holdings.remove(AssetId.of(payload.assetId()));
        return new WalletState(
                this.walletId,
                this.userId,
                this.holdings
        );
    }
}
