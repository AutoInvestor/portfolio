package io.autoinvestor.domain;

import io.autoinvestor.exceptions.InvalidBoughtPrice;

public class BoughtPrice {

    private final Integer boughtPrice;

    public BoughtPrice(Integer boughtPrice) {
        validate(boughtPrice);
        this.boughtPrice = boughtPrice;
    }

    private static void validate (Integer boughtPrice) {
        if (boughtPrice < 1) {
            throw InvalidBoughtPrice.with(boughtPrice);
        }
    }

    public Integer value () {
        return this.boughtPrice;
    }
}
