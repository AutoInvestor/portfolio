package io.autoinvestor.application.HoldingDeleteUseCase;

import io.autoinvestor.application.HoldingsReadModel;
import io.autoinvestor.application.UsersWalletReadModel;
import io.autoinvestor.domain.events.Event;
import io.autoinvestor.domain.events.EventPublisher;
import io.autoinvestor.domain.events.WalletEventStoreRepository;
import io.autoinvestor.domain.model.Wallet;
import io.autoinvestor.domain.model.WalletId;
import io.autoinvestor.exceptions.UserWithoutPortfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HoldingDeleteCommandHandler {

    private final UsersWalletReadModel usersWalletReadModel;
    private final WalletEventStoreRepository eventStore;
    private final HoldingsReadModel holdingsReadModel;
    private final EventPublisher eventPublisher;

    public void handle (HoldingDeleteCommand command) {
        String walletId = this.usersWalletReadModel.getWalletId(command.userId());
        if (walletId == null) {
            throw UserWithoutPortfolio.with(command.userId());
        }
        Wallet wallet = this.eventStore.get(WalletId.of(walletId))
                .orElseThrow(() -> UserWithoutPortfolio.with(command.userId()));
        wallet.deleteHolding(command.userId(), command.assetId());
        List<Event<?>> events = wallet.getUncommittedEvents();

        this.eventStore.save(wallet);

        boolean removed = this.holdingsReadModel.delete(command.userId(), command.assetId());
        if (!removed) {
            return;
        }
        this.eventPublisher.publish(events);
        wallet.markEventsAsCommitted();
    }
}
