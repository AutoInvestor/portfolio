package io.autoinvestor.domain.events;

import io.autoinvestor.domain.model.Wallet;
import io.autoinvestor.domain.model.WalletId;

import java.util.Optional;

public interface WalletEventStoreRepository {
    Optional<Wallet> get(WalletId walletId);
    void save(Wallet wallet);
}
