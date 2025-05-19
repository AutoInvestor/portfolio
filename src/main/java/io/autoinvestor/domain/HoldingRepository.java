package io.autoinvestor.domain;

import java.util.List;

public interface HoldingRepository {
    void save(List<Event<?>> holdingEvents);
}
