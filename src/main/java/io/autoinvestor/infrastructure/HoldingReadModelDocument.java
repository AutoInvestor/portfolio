package io.autoinvestor.infrastructure;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "holdingReadModel")
public record HoldingReadModelDocument(
        @Id String holdingId,
        String userId,
        String assetId,
        Integer amount,
        Integer boughtPrice
) {
}
