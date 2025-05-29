package io.autoinvestor.application;

public interface UsersWalletReadModel {
    void add(UsersWalletReadModelDTO dto);
    String getWalletId(String userId);
}
