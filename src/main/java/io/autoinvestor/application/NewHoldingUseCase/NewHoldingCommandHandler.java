package io.autoinvestor.application.NewHoldingUseCase;

import io.autoinvestor.domain.Event;
import io.autoinvestor.domain.EventPublisher;
import io.autoinvestor.domain.Holding;
import io.autoinvestor.domain.HoldingRepository;
import io.autoinvestor.infrastructure.HoldingAddedEventMessageMapper;
import io.autoinvestor.infrastructure.HoldingAddedMessage;
import io.autoinvestor.infrastructure.HoldingEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewHoldingCommandHandler {

    private final HoldingRepository holdingRepository;
    private final EventPublisher eventPublisher;
    private final HoldingAddedEventMessageMapper mapper;
    private final HoldingEventPublisher holdingEventPublisher;

    public void handle (NewHoldingCommand command) {
        Holding holding = Holding.create(command.userId(), command.assetId(), command.amount(), command.boughtPrice());
        List<Event<?>> uncommittedEvents = holding.releaseEvents();
        this.holdingRepository.save(uncommittedEvents);
        this.eventPublisher.publish(uncommittedEvents);
        List<HoldingAddedMessage> holdingAddedMessages = this.mapper.map(uncommittedEvents);
        this.holdingEventPublisher.publishHoldingAdded(holdingAddedMessages);
    }
}
