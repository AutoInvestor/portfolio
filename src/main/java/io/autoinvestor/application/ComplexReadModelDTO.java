package io.autoinvestor.application;

public record ComplexReadModelDTO(
        String userId,
        String assetId,
        Integer amount,
        Integer boughtPrice
) {
}
