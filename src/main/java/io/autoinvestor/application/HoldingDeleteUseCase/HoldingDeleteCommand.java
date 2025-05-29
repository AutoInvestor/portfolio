package io.autoinvestor.application.HoldingDeleteUseCase;

public record HoldingDeleteCommand(
        String userId,
        String assetId
) {
}
