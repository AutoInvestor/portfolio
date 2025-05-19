package io.autoinvestor.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record HoldingAddedMessage(@JsonProperty("eventId") String eventId,
                                  @JsonProperty("occurredAt") Date occurredAt,
                                  @JsonProperty("aggregateId") String aggregateId,
                                  @JsonProperty("version") int version,
                                  @JsonProperty("type") String type,
                                  @JsonProperty("payload") HoldingAddedMessagePayload payload)
{}

