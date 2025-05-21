package io.autoinvestor.exceptions;

public class InvalidAmount extends RuntimeException {
    private InvalidAmount(String message) {
        super(message);
    }

    public static InvalidAmount with (Integer amount) {
        String exceptionMessage = "Amount of " + amount + " is not valid";
        return new InvalidAmount(exceptionMessage);
    }
}
