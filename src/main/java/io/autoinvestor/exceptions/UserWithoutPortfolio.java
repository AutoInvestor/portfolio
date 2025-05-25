package io.autoinvestor.exceptions;

public class UserWithoutPortfolio extends RuntimeException {
    private UserWithoutPortfolio(String message) {
        super(message);
    }
    public static UserWithoutPortfolio with (String userId) {
        String message = "User with userId " + userId + " doesn't have a portfolio associated.";
        return new UserWithoutPortfolio(message);
    }
}
