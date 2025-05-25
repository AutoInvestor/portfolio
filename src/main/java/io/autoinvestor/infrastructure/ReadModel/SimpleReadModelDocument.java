package io.autoinvestor.infrastructure.ReadModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "walletUser")
public record SimpleReadModelDocument(
        String walletId,
        String userId
) {
}
