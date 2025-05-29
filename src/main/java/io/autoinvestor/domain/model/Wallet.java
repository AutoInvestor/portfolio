package io.autoinvestor.domain.model;

import io.autoinvestor.domain.events.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Wallet extends EventSourcedEntity {
    private WalletState state;

    private Wallet(List<Event<?>> stream) {
        super(stream);

        if (stream.isEmpty()) {
            this.state = WalletState.empty();
        }
    }

    public static Wallet empty() {
        return new Wallet(new ArrayList<>());
    }

    public static Wallet from(List<Event<?>> stream) {
        return new Wallet(stream);
    }

    public static Wallet create(String userId) {
        Wallet wallet = Wallet.empty();

        wallet.apply(WalletWasCreatedEvent.with(
                wallet.getState().getWalletId(),
                UserId.of(userId))
        );

        return wallet;
    }

    public void createHolding(String userId, String assetId, Integer amount, Integer boughtPrice) {
        this.apply(HoldingWasCreatedEvent.with(
                this.state.getWalletId(),
                UserId.of(userId),
                AssetId.of(assetId),
                Amount.of(amount),
                BoughtPrice.of(boughtPrice)
        ));
    }

    public void updateHolding(String userId, String assetId, Integer amount, Integer boughtPrice) {
        this.apply(HoldingWasUpdatedEvent.with(
                this.state.getWalletId(),
                UserId.of(userId),
                AssetId.of(assetId),
                Amount.of(amount),
                BoughtPrice.of(boughtPrice)
        ));
    }

    public void deleteHolding(String userId, String assetId) {
        this.apply(HoldingWasDeletedEvent.with(
                this.state.getWalletId(),
                UserId.of(userId),
                AssetId.of(assetId)
        ));
    }

    @Override
    protected void when(Event<?> e) {
        switch (e.getType()) {
            case WalletWasCreatedEvent.TYPE:
                whenWalletCreated((WalletWasCreatedEvent) e);
                break;
            case HoldingWasCreatedEvent.TYPE:
                whenHoldingCreated((HoldingWasCreatedEvent) e);
                break;
            case HoldingWasUpdatedEvent.TYPE:
                whenHoldingUpdated((HoldingWasUpdatedEvent) e);
                break;
            case HoldingWasDeletedEvent.TYPE:
                whenHoldingDeleted((HoldingWasDeletedEvent) e);
            default:
                throw new IllegalArgumentException("Unknown event type");
        }
    }

    private void whenWalletCreated(WalletWasCreatedEvent event) {
        if (this.state == null) {
            this.state = WalletState.empty();
        }
        this.state = this.state.withWalletCreated(event);
    }

    private void whenHoldingCreated(HoldingWasCreatedEvent event) {
        this.state = this.state.withHoldingCreated(event);
    }

    private void whenHoldingUpdated(HoldingWasUpdatedEvent event) {
        this.state = this.state.withHoldingUpdated(event);
    }

    private void whenHoldingDeleted(HoldingWasDeletedEvent event) {
        this.state = this.state.withHoldingDeleted(event);
    }
}
