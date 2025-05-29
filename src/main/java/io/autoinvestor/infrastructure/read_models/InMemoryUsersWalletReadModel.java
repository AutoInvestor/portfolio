package io.autoinvestor.infrastructure.read_models;

import io.autoinvestor.application.UsersWalletReadModel;
import io.autoinvestor.application.UsersWalletReadModelDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class InMemoryUsersWalletReadModel implements UsersWalletReadModel {
    @Override
    public void add(UsersWalletReadModelDTO dto) {

    }

    @Override
    public String getWalletId(String userId) {
        return "";
    }
}
