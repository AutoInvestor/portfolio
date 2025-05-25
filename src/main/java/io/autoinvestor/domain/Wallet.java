package io.autoinvestor.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Wallet extends AggregateRoot{

    @Getter
    private WalletState walletState;

    private Wallet(List<Event<?>> stream) {
        super(stream);
    }

    public static Wallet from(List<Event<?>> stream) {
        return new Wallet(stream);
    }

    @Override
    protected void when(Event<?> event) {
        switch (event.getType()) {
            case "WALLET_CREATED" :
                this.whenWalletCreated((WalletWasCreatedEvent) event);
                break;
            case "PORTFOLIO_ASSET_ADDED" :
                this.whenHoldingCreated((HoldingWasCreatedEvent) event);
                break;
            case "PORTFOLIO_ASSET_UPDATED" :
                this.whenHoldingUpdated((HoldingWasUpdatedEvent) event);
                break;
            case "PORTFOLIO_ASSET_REMOVED" :
                this.whenHoldingDeleted((HoldingWasDeletedEvent) event);

        }
    }

    private void whenWalletCreated(WalletWasCreatedEvent event) {
        this.walletState = WalletState.withWalletCreated(event);
    }
    private void whenHoldingCreated(HoldingWasCreatedEvent event) {
        this.walletState = this.walletState.withHoldingCreated(event);
    }
    private void whenHoldingUpdated(HoldingWasUpdatedEvent event) {
        this.walletState = this.walletState.withHoldingUpdated(event);
    }
    private void whenHoldingDeleted(HoldingWasDeletedEvent event){
        this.walletState = this.walletState.withHoldingDeleted(event);
    }

    public static Wallet empty() {
        return new Wallet(new ArrayList<>());
    }





    public static Wallet create(String userId) {
        WalletId id = WalletId.generate();
        UserId userIdDTO = new UserId(userId);
        Wallet wallet = Wallet.empty();
        wallet.createWallet(id, userIdDTO);
        return wallet;
    }

    public Wallet newHolding(String userId, String assetId, Integer amount, Integer boughtPrice) {
        UserId userIdDTO = UserId.of(userId);
        AssetId assetIdDTO = AssetId.of(assetId);
        Amount amountDTO = new Amount(amount);
        BoughtPrice boughtPriceDTO = new BoughtPrice(boughtPrice);
        createHolding(userIdDTO, assetIdDTO, amountDTO, boughtPriceDTO);
        return this;
    }

    public Wallet updateHolding(String userId,  String assetId, Integer amount, Integer boughtPrice) {
        UserId userIdDTO = UserId.of(userId);
        AssetId assetIdDTO = AssetId.of(assetId);
        Amount amountDTO = new Amount(amount);
        BoughtPrice boughtPriceDTO = new BoughtPrice(boughtPrice);
        updateHoldingApplier(userIdDTO, assetIdDTO, amountDTO, boughtPriceDTO);
        return this;
    }

    public Wallet deleteHolding(String assetId) {
        AssetId assetIdDTO = AssetId.of(assetId);
        deleteHoldingApplier(assetIdDTO);
        return this;
    }

    public void createWallet(WalletId walletId, UserId userId) {
        this.apply(WalletWasCreatedEvent.with(walletId, userId));
    }

    public void createHolding(UserId userId, AssetId assetId, Amount amount, BoughtPrice boughtPrice) {
        this.apply(HoldingWasCreatedEvent.with(
                walletState.walletId(), userId, assetId, amount, boughtPrice));
    }
    public void updateHoldingApplier(UserId userId, AssetId assetId, Amount amount, BoughtPrice boughtPrice) {
        this.apply(HoldingWasUpdatedEvent.with(walletState.walletId(), userId, assetId, amount, boughtPrice));
    }

    public void deleteHoldingApplier(AssetId assetId) {
        this.apply(HoldingWasDeletedEvent.with(walletState.walletId(), assetId));
    }

    public WalletState getState () {
        return walletState;
    }
}
