package io.autoinvestor.application;

import io.autoinvestor.infrastructure.HoldingReadModelDocument;

public interface HoldingReadModel {
    void add(HoldingReadModelDocument document);
}
