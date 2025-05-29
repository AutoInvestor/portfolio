package io.autoinvestor.application.UptdateHoldingUseCase;

import io.autoinvestor.application.HoldingsReadModel;
import io.autoinvestor.application.HoldingsReadModelDTO;
import io.autoinvestor.application.UsersWalletReadModel;
import io.autoinvestor.domain.events.EventPublisher;
import io.autoinvestor.domain.model.AssetId;
import io.autoinvestor.domain.events.Event;
import io.autoinvestor.domain.model.Wallet;
import io.autoinvestor.domain.events.WalletEventStoreRepository;
import io.autoinvestor.domain.model.WalletId;
import io.autoinvestor.exceptions.UserWithoutPortfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateHoldingCommandHandler {

    private final UsersWalletReadModel usersWalletReadModel;
    private final HoldingsReadModel holdingsReadModel;
    private final WalletEventStoreRepository eventStore;
    private final EventPublisher eventPublisher;

    public void handle (UpdateHoldingCommand command) {
        String walletId = this.usersWalletReadModel.getWalletId(command.userId());
        if (walletId == null) {
            throw UserWithoutPortfolio.with(command.userId());
        }

        Wallet wallet = this.eventStore.get(WalletId.of(walletId))
                .orElseThrow(() -> UserWithoutPortfolio.with(command.userId()));

        wallet.updateHolding(command.userId(), command.assetId(), command.amount(), command.boughtPrice());

        List<Event<?>> events = wallet.getUncommittedEvents();

        this.eventStore.save(wallet);

        HoldingsReadModelDTO dto = new HoldingsReadModelDTO(
                wallet.getState().getUserId().value(),
                command.assetId(),
                wallet.getState().getHoldings().get(AssetId.of(command.assetId())).amount().value(),
                wallet.getState().getHoldings().get(AssetId.of(command.assetId())).boughtPrice().value()
        );
        this.holdingsReadModel.update(dto);

        this.eventPublisher.publish(events);

        wallet.markEventsAsCommitted();
    }
}
