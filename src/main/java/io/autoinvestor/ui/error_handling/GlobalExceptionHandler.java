package io.autoinvestor.ui.error_handling;

import io.autoinvestor.exceptions.AssetAlreadyExists;
import io.autoinvestor.exceptions.UserWithoutPortfolio;
import io.autoinvestor.ui.PortfolioController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {PortfolioController.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(
            MissingRequestHeaderException ex) {
        return ErrorResponse.builder().status(400).message(ex.getMessage()).build();
    }

    @ExceptionHandler(UserWithoutPortfolio.class)
    public ResponseEntity<ErrorResponse> handleUserWithoutPortfolio(UserWithoutPortfolio ex) {
        return ErrorResponse.builder().status(400).message(ex.getMessage()).build();
    }

    @ExceptionHandler(AssetAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleAssetALreadyExists(AssetAlreadyExists ex) {
        return ErrorResponse.builder().status(400).message(ex.getMessage()).build();
    }
}
