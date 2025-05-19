package io.autoinvestor.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Holding extends AggregateRoot{

    @Getter
    private HoldingState holdingState;

    private Holding(List<Event<?>> stream) {
        super(stream);
    }

    @Override
    protected void when(Event<?> event) {
        switch (event.getType()) {
            case "PORTFOLIO_HOLDING_ADDED" :
                this.whenHoldingAdded((HoldingWasAddedEvent) event);
                break;
        }
    }

    public static Holding empty() {
        return new Holding(new ArrayList<>());
    }

    private void whenHoldingAdded(HoldingWasAddedEvent event) {
        this.holdingState = HoldingState.withHoldingAdded(event);
    }

    public static Holding create(String userId, String assetId, Integer amount, Integer boughtPrice) {
        HoldingId id = HoldingId.generate();
        UserId userIdDTO = new UserId(userId);
        AssetId assetIdDTO = new AssetId(assetId);
        Amount amountDTO = new Amount(amount);
        BoughtPrice boughtPriceDTO = new BoughtPrice(boughtPrice);
        Holding holding = Holding.empty();
        holding.createHolding(id, userIdDTO, assetIdDTO, amountDTO, boughtPriceDTO);
        return holding;
    }

    public void createHolding(HoldingId holdingId, UserId userId, AssetId assetId, Amount amount, BoughtPrice boughtPrice) {
        this.apply(HoldingWasAddedEvent.with(holdingId, userId, assetId, amount, boughtPrice));
    }
}
