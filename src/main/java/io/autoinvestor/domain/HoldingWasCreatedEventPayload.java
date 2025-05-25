package io.autoinvestor.domain;

import java.util.Map;

public record HoldingWasCreatedEventPayload(
        UserId userId,
        AssetId assetId,
        Amount amount,
        BoughtPrice boughtPrice
) implements EventPayload{
    @Override
    public Map<String, Object> asMap() {
        return Map.of(
                "userId", userId,
                "assetId", assetId,
                "amount", amount,
                "boughtPrice", boughtPrice
        );
    }
}