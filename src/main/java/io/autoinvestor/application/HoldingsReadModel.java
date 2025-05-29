package io.autoinvestor.application;

import java.util.List;

public interface HoldingsReadModel {
    void add(HoldingsReadModelDTO dto);
    void update(HoldingsReadModelDTO dto);
    List<HoldingsReadModelDTO> getHoldings(String userId);
}
