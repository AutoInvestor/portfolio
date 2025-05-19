package io.autoinvestor.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HoldingAddedMessagePayload(
        @JsonProperty("userId") String userId,
        @JsonProperty("assetId") String assetId,
        @JsonProperty("amount") Integer amount,
        @JsonProperty("boughtPrice") Integer boughtPrice)
    {}
