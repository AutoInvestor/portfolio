package io.autoinvestor.application.WalletCreatedUseCase;
import io.autoinvestor.application.SimpleReadModelDTO;
import io.autoinvestor.application.ReadModel;
import io.autoinvestor.domain.Event;
import io.autoinvestor.domain.WalletRepository;
import io.autoinvestor.domain.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WalletCreatedHandler {

    private final WalletRepository eventRepository;
    private final ReadModel readModel;

    public void handle(WalletCreateCommand command) {
        System.out.println("Mensaje recibido en WalletCreatedHandler: " + command.userId());
        Wallet wallet = Wallet.create(command.userId());
        List<Event<?>> uncomittedEvents = wallet.releaseEvents();
        this.eventRepository.save(uncomittedEvents);
        SimpleReadModelDTO dto = new SimpleReadModelDTO(
                wallet.getState().walletId().value(),
                wallet.getState().userId().value()
        );
        readModel.add(dto);


    }
}

