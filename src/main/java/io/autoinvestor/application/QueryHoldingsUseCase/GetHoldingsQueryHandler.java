package io.autoinvestor.application.QueryHoldingsUseCase;

import io.autoinvestor.application.HoldingReadModel;
import io.autoinvestor.infrastructure.HoldingReadModelDocument;
import io.autoinvestor.ui.GetHoldingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetHoldingsQueryHandler {

    private final HoldingReadModel holdingReadModel;

    public List<HoldingReadModelDocument> handle (GetHoldingsQuery query) {
        return holdingReadModel.get(query.userId());
    }
}
