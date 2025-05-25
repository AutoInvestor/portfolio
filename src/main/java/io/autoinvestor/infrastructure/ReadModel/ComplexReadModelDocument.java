package io.autoinvestor.infrastructure.ReadModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "holdings")
public record ComplexReadModelDocument(
        String userId,
        String assetId,
        Integer amount,
        Integer boughtPrice
) {}
