package io.autoinvestor.domain.model;

import io.autoinvestor.exceptions.InvalidBoughtPrice;

public class BoughtPrice {

    private final Integer boughtPrice;

    BoughtPrice(Integer boughtPrice) {
        this.boughtPrice = boughtPrice;
    }

    public static BoughtPrice of(Integer boughtPrice) {
        validate(boughtPrice);
        return new BoughtPrice(boughtPrice);
    }

    private static void validate (Integer boughtPrice) {
        if (boughtPrice < 1) {
            throw InvalidBoughtPrice.with(boughtPrice);
        }
    }

    public int value() {
        return this.boughtPrice;
    }
}
