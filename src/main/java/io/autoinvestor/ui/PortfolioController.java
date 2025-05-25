package io.autoinvestor.ui;

import io.autoinvestor.application.ComplexReadModelDTO;
import io.autoinvestor.application.NewHoldingUseCase.NewHoldingCommand;
import io.autoinvestor.application.NewHoldingUseCase.NewHoldingCommandHandler;
import io.autoinvestor.application.QueryHoldingsUseCase.GetHoldingsQuery;
import io.autoinvestor.application.QueryHoldingsUseCase.GetHoldingsQueryHandler;
import io.autoinvestor.application.UptdateHoldingUseCase.UpdateHoldingCommand;
import io.autoinvestor.application.UptdateHoldingUseCase.UpdateHoldingCommandHandler;
import io.autoinvestor.application.WalletCreatedUseCase.WalletCreateCommand;
import io.autoinvestor.application.WalletCreatedUseCase.WalletCreatedHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio/holdings")
@RequiredArgsConstructor
public class PortfolioController {

    private final NewHoldingCommandHandler newHoldingCommandHandler;
    private final GetHoldingsQueryHandler getHoldingsQueryHandler;
    private final WalletCreatedHandler walletCreatedHandler;
    private final GetHoldingResponseDocumentMapper mapperGetHoldingResponse;
    private final UpdateHoldingCommandHandler updateHoldingCommandHandler;
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
        List<ComplexReadModelDTO> documents = getHoldingsQueryHandler.handle(new GetHoldingsQuery(userId));

        return ResponseEntity.ok(documents.stream().map(mapperGetHoldingResponse::map).toList());
    }
    @PutMapping
    public ResponseEntity<Void> putHolding (
            @RequestHeader (value = "X-User-Id", required = true) String userId,
            @Valid @RequestBody HoldingRequestDTO holdingRequestDTO
    ) {
        updateHoldingCommandHandler.handle(new UpdateHoldingCommand(
                userId,
                holdingRequestDTO.assetId(),
                holdingRequestDTO.amount(),
                holdingRequestDTO.boughtPrice()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/user")
    public ResponseEntity<Void> simulateIncomingUserCreatedMessage (
            @RequestBody SimulateUserIncomingMessageDTO dto
            ) {
        walletCreatedHandler.handle(new WalletCreateCommand(dto.payload().userId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
