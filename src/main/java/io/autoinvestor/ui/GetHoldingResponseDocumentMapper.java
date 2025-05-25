package io.autoinvestor.ui;

import io.autoinvestor.application.ComplexReadModelDTO;
import io.autoinvestor.infrastructure.ReadModel.ComplexReadModelDocument;
import org.springframework.stereotype.Service;

@Service
public class GetHoldingResponseDocumentMapper {

public GetHoldingResponse map(ComplexReadModelDTO document) {
    return new GetHoldingResponse(
            document.assetId(),
            document.amount(),
            document.boughtPrice()
    );
    }
}
