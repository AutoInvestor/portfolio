package io.autoinvestor.application;

import io.autoinvestor.infrastructure.HoldingReadModelDocument;
import io.autoinvestor.ui.GetHoldingResponse;

import java.util.List;

public interface HoldingReadModel {
    void add(HoldingReadModelDocument document);
    List<HoldingReadModelDocument> get(String userId);
}
