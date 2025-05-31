package io.autoinvestor.application;

import java.util.List;

public interface HoldingsReadModel {
    void add(HoldingsReadModelDTO dto);

    void update(HoldingsReadModelDTO dto);

    boolean delete(String userId, String assetId);

    List<HoldingsReadModelDTO> getHoldings(String userId);

    boolean assetAlreadyExists(String userIs, String assetId);
}
