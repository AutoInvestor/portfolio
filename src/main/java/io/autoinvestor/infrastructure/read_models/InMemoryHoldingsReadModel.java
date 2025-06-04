package io.autoinvestor.infrastructure.read_models;

import io.autoinvestor.application.HoldingsReadModel;
import io.autoinvestor.application.HoldingsReadModelDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class InMemoryHoldingsReadModel implements HoldingsReadModel {

    Collection<HoldingsReadModelDTO> holdings = new ArrayList<>();

    public InMemoryHoldingsReadModel () {
        holdings.add(new HoldingsReadModelDTO("user-1",
                "asset-1",
                1,
                1));
    }
    @Override
    public void add(HoldingsReadModelDTO dto) {
        holdings.add(dto);
    }

    @Override
    public void update(HoldingsReadModelDTO dto) {}

    @Override
    public boolean delete(String userId, String assetId) {
        return false;
    }

    @Override
    public List<HoldingsReadModelDTO> getHoldings(String userId) {
        return holdings.stream()
                .filter(doc -> doc.userId().equals(userId))
                .toList();
    }

    @Override
    public boolean assetAlreadyExists(String userIs, String assetId) {
        return false;
    }

    public void clear() {
        holdings.clear();
    }
}
