package io.autoinvestor.domain;

public class HoldingId extends Id{
    HoldingId(String id) {
        super(id);
    }

    public static HoldingId generate() {
        return new HoldingId(generateId());
    }
}
