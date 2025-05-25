package io.autoinvestor.application.DeleteHoldingUseCase;

public record DeleteHoldingCommand(
        String assetId,
        String userId
) {
}
