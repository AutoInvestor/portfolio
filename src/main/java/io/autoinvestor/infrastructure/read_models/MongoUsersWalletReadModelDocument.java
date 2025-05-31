package io.autoinvestor.infrastructure.read_models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public record MongoUsersWalletReadModelDocument(@Id String userId, String walletId) {}
