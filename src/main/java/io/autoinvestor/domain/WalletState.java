package io.autoinvestor.domain;


import java.util.HashMap;
import java.util.Map;

public record WalletState(
    WalletId walletId,
    UserId userId,
    Map<AssetId, Holding> holdings
) {
    public static WalletState withWalletCreated(WalletWasCreatedEvent event) {
        WalletWasCreatedPayload payload = event.getPayload();
        return new WalletState(
                (WalletId) event.getAggregateId(),
                payload.userId(),
                new HashMap<>()
        );
    }

    public WalletState withHoldingCreated(HoldingWasCreatedEvent event) {
        HoldingWasCreatedEventPayload payload = event.getPayload();
        WalletId walletId = (WalletId) event.getAggregateId();
        UserId userId = payload.userId();
        Holding newHolding = new Holding(payload.amount(), payload.boughtPrice());
        Map<AssetId, Holding> holdingsUpdated =  new HashMap<>(this.holdings);
        holdingsUpdated.put(payload.assetId(), newHolding);
        return new WalletState(
                this.walletId,
                this.userId,
                holdingsUpdated
        );
    }

    public WalletState withHoldingUpdated(HoldingWasUpdatedEvent event) {
        HoldingWasUpdatedEventPayload payload = event.getPayload();
        Holding holdingUpdated = new Holding(payload.amount(), payload.boughtPrice());
        holdings.put(payload.assetId(), holdingUpdated);
        return new WalletState(
                this.walletId,
                this.userId,
                this.holdings
        );
    }

    public WalletState withHoldingDeleted(HoldingWasDeletedEvent event) {
        AssetId assetIdToDelete = event.getPayload().assetId();
        this.holdings.remove(assetIdToDelete);
        return new WalletState(
                this.walletId,
                this.userId,
                this.holdings
        );
    }

}
