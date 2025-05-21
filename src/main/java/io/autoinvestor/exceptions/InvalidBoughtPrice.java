package io.autoinvestor.exceptions;

public class InvalidBoughtPrice extends RuntimeException {
    private InvalidBoughtPrice(String message) {
        super(message);
    }

    public static InvalidBoughtPrice with (Integer boughtPrice) {
        String exceptionMessage = "The bought price of " + boughtPrice +
                " is not valid. It should be greater than 0.";
        return new InvalidBoughtPrice(exceptionMessage);
    }
}
