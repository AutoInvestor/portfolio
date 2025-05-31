package io.autoinvestor.exceptions;

public class AssetAlreadyExists extends RuntimeException {
    private AssetAlreadyExists(String message) {
        super(message);
    }

    public static AssetAlreadyExists with(String userId, String assetId) {
        String message = "Duplicate asset " + assetId + " for user " + userId;
        return new AssetAlreadyExists(message);
    }
}
