package io.autoinvestor.exceptions;

public class AssetNotFound extends RuntimeException {
    private AssetNotFound(String message) {
        super(message);
    }

    public static AssetNotFound with (String assetId) {
        String errorMessage = "Asset with assetId " + assetId + " doesn't exist";
        return new AssetNotFound(errorMessage);
    }
}
