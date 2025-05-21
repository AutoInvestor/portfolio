package io.autoinvestor.ui;

import io.autoinvestor.infrastructure.HoldingReadModelDocument;
import org.springframework.stereotype.Service;

@Service
public class GetHoldingResponseDocumentMapper {

public static GetHoldingResponse map(HoldingReadModelDocument document) {
    return new GetHoldingResponse(
            document.assetId(),
            document.amount(),
            document.boughtPrice()
    );
    }
}
