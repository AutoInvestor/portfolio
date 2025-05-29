package io.autoinvestor.domain.events;

import java.util.Map;

public record WalletWasCreatedEventPayload(
        String userId
) implements EventPayload {
    @Override
    public Map<String, Object> asMap() {
        return Map.of(
                "userId", userId
        );
    }
}
