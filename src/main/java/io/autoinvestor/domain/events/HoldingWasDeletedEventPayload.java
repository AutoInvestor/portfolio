package io.autoinvestor.domain.events;

import java.util.Map;

public record HoldingWasDeletedEventPayload(
        String userId,
        String assetId
) implements EventPayload {
    @Override
    public Map<String, Object> asMap() {
        return Map.of(
                "userId" , userId,
                "assetId", assetId
        );
    }
}
