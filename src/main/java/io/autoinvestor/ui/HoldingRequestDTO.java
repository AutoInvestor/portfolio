package io.autoinvestor.ui;

import jakarta.validation.constraints.NotNull;

public record HoldingRequestDTO(
        @NotNull String assetId,
        @NotNull Integer amount,
        @NotNull Integer boughtPrice
        ) {}
