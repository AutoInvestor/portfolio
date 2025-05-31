package io.autoinvestor.infrastructure.read_models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "holdings")
public record MongoHoldingsReadModelDocument(
        String userId, String assetId, Integer amount, Integer boughtPrice) {}
