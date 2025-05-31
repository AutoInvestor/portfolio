package io.autoinvestor.application;

public record HoldingsReadModelDTO(
        String userId, String assetId, Integer amount, Integer boughtPrice) {}
