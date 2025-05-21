package io.autoinvestor.infrastructure;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "holdingCreated")
public record HoldingAddedEventDocument(
        @Id String id,
        String aggregateId,
        String userId,
        String assetId,
        Integer amount,
        Integer boughtPrice,
        @Field("occurredAt")Instant ocurredAt,
        Integer version
        ) {}
