package io.autoinvestor.domain;

import io.autoinvestor.exceptions.InvalidAmount;

public class Amount {
    private final Integer amount;

    public Amount(Integer amount) {
        validate(amount);
        this.amount = amount;
    }

    private static void validate(Integer amount) {
        if (amount < 1) {
            throw InvalidAmount.with(amount);
        }
    }

    public Integer value() {
        return this.amount;
    }
}
