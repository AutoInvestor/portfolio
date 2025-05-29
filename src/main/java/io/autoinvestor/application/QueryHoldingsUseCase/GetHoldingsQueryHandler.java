package io.autoinvestor.application.QueryHoldingsUseCase;

import io.autoinvestor.application.HoldingsReadModel;
import io.autoinvestor.application.HoldingsReadModelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetHoldingsQueryHandler {

    private final HoldingsReadModel readModel;

    public List<HoldingsReadModelDTO> handle (GetHoldingsQuery query) {
        return this.readModel.getHoldings(query.userId());
    }
}
