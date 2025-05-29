package io.autoinvestor.ui;

import io.autoinvestor.application.HoldingsReadModelDTO;
import org.springframework.stereotype.Service;

@Service
public class GetHoldingResponseDocumentMapper {

public GetHoldingResponse map(HoldingsReadModelDTO document) {
    return new GetHoldingResponse(
            document.assetId(),
            document.amount(),
            document.boughtPrice()
    );
    }
}
