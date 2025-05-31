package io.autoinvestor.application.NewHoldingUseCase;

import io.autoinvestor.application.HoldingsReadModel;
import io.autoinvestor.application.HoldingsReadModelDTO;
import io.autoinvestor.application.UsersWalletReadModel;
import io.autoinvestor.domain.events.Event;
import io.autoinvestor.domain.events.EventPublisher;
import io.autoinvestor.domain.events.WalletEventStoreRepository;
import io.autoinvestor.domain.model.AssetId;
import io.autoinvestor.domain.model.Wallet;
import io.autoinvestor.domain.model.WalletId;
import io.autoinvestor.exceptions.AssetAlreadyExists;
import io.autoinvestor.exceptions.UserWithoutPortfolio;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewHoldingCommandHandler {

    private final WalletEventStoreRepository eventStore;
    private final UsersWalletReadModel usersWalletReadModel;
    private final HoldingsReadModel holdingsReadModel;
    private final EventPublisher eventPublisher;

    public void handle(NewHoldingCommand command) {
        String walletId = this.usersWalletReadModel.getWalletId(command.userId());
        if (walletId == null) {
            throw UserWithoutPortfolio.with(command.userId());
        }

        if (this.holdingsReadModel.assetAlreadyExists(command.userId(), command.assetId())) {
            throw AssetAlreadyExists.with(command.userId(), command.assetId());
        }
        Wallet wallet =
                this.eventStore
                        .get(WalletId.of(walletId))
                        .orElseThrow(() -> UserWithoutPortfolio.with(command.userId()));

        wallet.createHolding(
                command.userId(), command.assetId(), command.amount(), command.boughtPrice());

        List<Event<?>> events = wallet.getUncommittedEvents();

        this.eventStore.save(wallet);

        HoldingsReadModelDTO dto =
                new HoldingsReadModelDTO(
                        wallet.getState().getUserId().value(),
                        command.assetId(),
                        wallet.getState()
                                .getHoldings()
                                .get(AssetId.of(command.assetId()))
                                .amount()
                                .value(),
                        wallet.getState()
                                .getHoldings()
                                .get(AssetId.of(command.assetId()))
                                .boughtPrice()
                                .value());
        this.holdingsReadModel.add(dto);

        this.eventPublisher.publish(events);

        wallet.markEventsAsCommitted();
    }
}
