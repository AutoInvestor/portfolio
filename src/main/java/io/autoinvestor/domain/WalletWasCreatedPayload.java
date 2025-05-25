package io.autoinvestor.domain;

import java.util.Map;

public record WalletWasCreatedPayload(
        UserId userId
) implements EventPayload{


    @Override
    public Map<String, Object> asMap() {
        return Map.of(
                "userId", userId
        );
    }
}
