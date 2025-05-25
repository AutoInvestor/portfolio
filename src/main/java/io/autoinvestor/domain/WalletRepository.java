package io.autoinvestor.domain;

import java.util.List;

public interface WalletRepository {
    void save(List<Event<?>> walletEvents);
    Wallet get (String walletId);
}
