package io.autoinvestor.domain.model;

import io.autoinvestor.domain.Id;

public class WalletId extends Id {
    WalletId(String id) {
        super(id);
    }

    public static WalletId generate() {
        return new WalletId(generateId());
    }

    public static WalletId of(String id) {
        return new WalletId(id);
    }
}
