package io.autoinvestor.application.NewHoldingUseCase;

public record NewHoldingCommand(
        String assetId, String userId, Integer amount, Integer boughtPrice) {}
