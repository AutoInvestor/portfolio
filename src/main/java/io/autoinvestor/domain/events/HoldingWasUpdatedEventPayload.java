package io.autoinvestor.domain.events;

import java.util.Map;

public record HoldingWasUpdatedEventPayload(
        String userId,
        String assetId,
        int amount,
        int boughtPrice
) implements EventPayload {
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
