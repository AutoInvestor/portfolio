package io.autoinvestor.ui.error_handling;

public record ErrorResponse(int status, String error) {
    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }
}
