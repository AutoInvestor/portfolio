package io.autoinvestor.ui;

import jakarta.validation.constraints.NotNull;

public record HoldingRequestDTO(
        String assetId,
        Integer amount,
        Integer boughtPrice
        ) {}
