package io.autoinvestor.domain.model;

import io.autoinvestor.domain.Id;

public class UserId extends Id {
    public UserId(String id){
        super(id);
    }

    public static UserId generate() {
        return new UserId(generateId());
    }

    public static UserId of(String userId){
        return new UserId(userId);
    }

    public static UserId empty() {
        return new UserId("");
    }
}
