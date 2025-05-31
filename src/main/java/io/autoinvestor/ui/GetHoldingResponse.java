package io.autoinvestor.ui;

public record GetHoldingResponse(String assetId, Integer amount, Integer boughtPrice) {}
