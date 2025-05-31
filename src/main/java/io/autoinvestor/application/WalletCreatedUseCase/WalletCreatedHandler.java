package io.autoinvestor.application.WalletCreatedUseCase;

import io.autoinvestor.application.UsersWalletReadModel;
import io.autoinvestor.application.UsersWalletReadModelDTO;
import io.autoinvestor.domain.events.Event;
import io.autoinvestor.domain.events.EventPublisher;
import io.autoinvestor.domain.events.WalletEventStoreRepository;
import io.autoinvestor.domain.model.Wallet;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletCreatedHandler {

    private final WalletEventStoreRepository eventStore;
    private final EventPublisher eventPublisher;
    private final UsersWalletReadModel readModel;

    public void handle(WalletCreateCommand command) {
        Wallet wallet = Wallet.create(command.userId());

        List<Event<?>> events = wallet.getUncommittedEvents();

        this.eventStore.save(wallet);

        UsersWalletReadModelDTO dto =
                new UsersWalletReadModelDTO(
                        wallet.getState().getWalletId().value(),
                        wallet.getState().getUserId().value());
        this.readModel.add(dto);

        this.eventPublisher.publish(events);

        wallet.markEventsAsCommitted();
    }
}
