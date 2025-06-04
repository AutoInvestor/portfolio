package io.autoinvestor.infrastructure.read_models;

import io.autoinvestor.application.UsersWalletReadModel;
import io.autoinvestor.application.UsersWalletReadModelDTO;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class InMemoryUsersWalletReadModel implements UsersWalletReadModel {

    private final Map<String, String> readModel = new HashMap<>();

    public InMemoryUsersWalletReadModel() {
        readModel.put("user-1", "wallet-1");
        readModel.put("user-2", "wallet-2");
    }

    @Override
    public void add(UsersWalletReadModelDTO dto) {
        readModel.put(dto.userId(), dto.walletId());
    }

    @Override
    public String getWalletId(String userId) {
        return readModel.get(userId);
    }

    public void clear() {
        readModel.clear();
    }
}
