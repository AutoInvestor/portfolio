package io.autoinvestor.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HoldingDeletedMessagePayload(@JsonProperty("userId") String userId,
                                           @JsonProperty("assetId") String assetId
) {}
