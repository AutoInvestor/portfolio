package io.autoinvestor.domain.model;

public record Holding(
        Amount amount,
        BoughtPrice boughtPrice
) {
    public static Holding of(Amount amount, BoughtPrice boughtPrice) {
        return new Holding(amount, boughtPrice);
    }
}
