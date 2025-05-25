package io.autoinvestor.application;

import io.autoinvestor.domain.Holding;
import io.autoinvestor.infrastructure.ReadModel.ComplexReadModelDocument;

import java.util.List;
import java.util.Map;

public interface ReadModel {
    void add(SimpleReadModelDTO dto);
    void add(ComplexReadModelDTO dto);
    String getWalletId(String userId);
    void update(ComplexReadModelDTO dto);
    List<ComplexReadModelDTO>  getHoldings (String userId);
}
