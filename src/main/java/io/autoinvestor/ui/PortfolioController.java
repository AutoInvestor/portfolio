package io.autoinvestor.ui;

import io.autoinvestor.application.NewHoldingUseCase.NewHoldingCommand;
import io.autoinvestor.application.NewHoldingUseCase.NewHoldingCommandHandler;
import io.autoinvestor.application.QueryHoldingsUseCase.GetHoldingsQuery;
import io.autoinvestor.application.QueryHoldingsUseCase.GetHoldingsQueryHandler;
import io.autoinvestor.infrastructure.HoldingReadModelDocument;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final NewHoldingCommandHandler newHoldingCommandHandler;
    private final GetHoldingsQueryHandler getHoldingsQueryHandler;
    @PostMapping
    public ResponseEntity<Void> addHolding(
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @Valid @RequestBody HoldingRequestDTO holdingRequestDTO) {

        newHoldingCommandHandler.handle(new NewHoldingCommand(
                holdingRequestDTO.assetId(),
                userId,
                holdingRequestDTO.amount(),
                holdingRequestDTO.boughtPrice()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<GetHoldingResponse>> getHoldings(
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ) {
        List<HoldingReadModelDocument> documents = getHoldingsQueryHandler.handle(new GetHoldingsQuery(userId));

        return ResponseEntity.ok(documents.stream().map(GetHoldingResponseDocumentMapper::map).toList());
    }
}
