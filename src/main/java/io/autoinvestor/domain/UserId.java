package io.autoinvestor.domain;

public class UserId extends Id{

    UserId(String id){
        super(id);
    }
    public static UserId generate() {
        return new UserId(generateId());
    }
}
