package io.autoinvestor.application.QueryHoldingsUseCase;

import io.autoinvestor.application.ComplexReadModelDTO;
import io.autoinvestor.application.ReadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetHoldingsQueryHandler {

    private final ReadModel readModel;

    public List<ComplexReadModelDTO> handle (GetHoldingsQuery query) {
        return readModel.getHoldings(query.userId());
    }
}
