package io.autoinvestor.ui;

import io.autoinvestor.application.NewHoldingUseCase.NewHoldingCommand;
import io.autoinvestor.application.NewHoldingUseCase.NewHoldingCommandHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final NewHoldingCommandHandler newHoldingCommandHandler;
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
}
