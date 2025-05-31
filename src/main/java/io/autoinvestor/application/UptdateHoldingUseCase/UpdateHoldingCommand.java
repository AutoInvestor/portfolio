package io.autoinvestor.application.UptdateHoldingUseCase;

public record UpdateHoldingCommand(
        String userId, String assetId, Integer amount, Integer boughtPrice) {}
