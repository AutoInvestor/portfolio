package io.autoinvestor.infrastructure.read_models;

import io.autoinvestor.application.HoldingsReadModel;
import io.autoinvestor.application.HoldingsReadModelDTO;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class InMemoryHoldingsReadModel implements HoldingsReadModel {
    @Override
    public void add(HoldingsReadModelDTO dto) {}

    @Override
    public void update(HoldingsReadModelDTO dto) {}

    @Override
    public boolean delete(String userId, String assetId) {
        return false;
    }

    @Override
    public List<HoldingsReadModelDTO> getHoldings(String userId) {
        return List.of();
    }

    @Override
    public boolean assetAlreadyExists(String userIs, String assetId) {
        return false;
    }
}
