package io.autoinvestor.application.DeleteHoldingUseCase;

import io.autoinvestor.application.ComplexReadModelDTO;
import io.autoinvestor.application.ReadModel;
import io.autoinvestor.domain.*;
import io.autoinvestor.infrastructure.EventMessageMapper;
import io.autoinvestor.infrastructure.EventPublisherQueue;
import io.autoinvestor.infrastructure.HoldingDeletedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteHoldingCommandHandler {

    private final ReadModel readModel;
    private final WalletRepository eventRepository;
    private final EventMessageMapper messageMapper;
    private final EventPublisherQueue eventPublisher;

    public void handle (DeleteHoldingCommand command) {
        String walletId = readModel.getWalletId(command.userId());
        Wallet wallet = eventRepository.get(walletId);
        wallet.deleteHolding(command.assetId());
        List<Event<?>> uncommittedEvents = wallet.releaseEvents();
        this.eventRepository.save(uncommittedEvents);
        this.readModel.removeHolding(command.userId(), command.assetId());
        List<HoldingDeletedMessage> holdingDeletedMessages = this.messageMapper.mapToHoldingDeletedMessage(command.userId(), uncommittedEvents);
        this.eventPublisher.publishHoldingDeleted(holdingDeletedMessages);
    }
}
