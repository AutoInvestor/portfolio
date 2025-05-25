package io.autoinvestor.infrastructure;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.Null;
import org.checkerframework.checker.units.qual.N;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "walletEvent")
public record WalletEventDocument(
        @Id String id,
        String eventType,
        String walletId,
        String userId,
        @Nullable String assetId,
        @Nullable Integer amount,
        @Nullable Integer boughtPrice,
        @Field("occurredAt")Instant ocurredAt,
        Integer version
        ) {}
