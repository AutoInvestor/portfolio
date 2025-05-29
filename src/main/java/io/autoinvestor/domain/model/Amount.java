package io.autoinvestor.domain.model;

import io.autoinvestor.exceptions.InvalidAmount;

public class Amount {
    private final Integer amount;

    Amount(Integer amount) {
        this.amount = amount;
    }

    public static Amount of(Integer amount) {
        validate(amount);
        return new Amount(amount);
    }

    private static void validate(Integer amount) {
        if (amount < 1) {
            throw InvalidAmount.with(amount);
        }
    }

    public int value() {
        return this.amount;
    }
}
