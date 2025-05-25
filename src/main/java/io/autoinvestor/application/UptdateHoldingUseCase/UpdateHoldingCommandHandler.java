package io.autoinvestor.application.UptdateHoldingUseCase;

import io.autoinvestor.application.ComplexReadModelDTO;
import io.autoinvestor.application.ReadModel;
import io.autoinvestor.domain.AssetId;
import io.autoinvestor.domain.Event;
import io.autoinvestor.domain.Wallet;
import io.autoinvestor.domain.WalletRepository;
import io.autoinvestor.exceptions.UserWithoutPortfolio;
import io.autoinvestor.infrastructure.EventMessageMapper;
import io.autoinvestor.infrastructure.EventPublisherQueue;
import io.autoinvestor.infrastructure.HoldingAddedOrUpdatedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateHoldingCommandHandler {

    private final ReadModel readModel;
    private final WalletRepository eventRepository;
    private final EventMessageMapper messageMapper;
    private final EventPublisherQueue eventPublisher;

    public void handle (UpdateHoldingCommand command) {
        String walletId = readModel.getWalletId(command.userId());
        if (walletId == null) {
            throw UserWithoutPortfolio.with(command.userId());
        }
        Wallet wallet = eventRepository.get(walletId);
        wallet.updateHolding(command.userId(), command.assetId(), command.amount(), command.boughtPrice());
        List<Event<?>> uncommittedEvents = wallet.releaseEvents();
        this.eventRepository.save(uncommittedEvents);
        ComplexReadModelDTO dto = new ComplexReadModelDTO(
                wallet.getState().userId().value(),
                command.assetId(),
                wallet.getState().holdings().get(AssetId.of(command.assetId())).amount().value(),
                wallet.getState().holdings().get(AssetId.of(command.assetId())).boughtPrice().value()

        );
        this.readModel.update(dto);
        List<HoldingAddedOrUpdatedMessage> holdingUpdatedMessages = this.messageMapper.mapToHoldingUpdatedMessage(uncommittedEvents);
        this.eventPublisher.publishHoldingAddedOrUpdated(holdingUpdatedMessages);
    }
}
