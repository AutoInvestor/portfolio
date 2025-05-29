package io.autoinvestor.infrastructure.repositories;

import io.autoinvestor.domain.events.Event;
import io.autoinvestor.domain.events.WalletEventStoreRepository;
import io.autoinvestor.domain.model.Wallet;
import io.autoinvestor.domain.model.WalletId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
@Profile("local")
public class InMemoryEventStoreRepository implements WalletEventStoreRepository {

    private final List<Event<?>> eventStore = new CopyOnWriteArrayList<>();

    @Override
    public void save(Wallet wallet) {
        eventStore.addAll(wallet.getUncommittedEvents());
    }

    @Override
    public Optional<Wallet> get(WalletId walletId) {
        List<Event<?>> events = eventStore.stream()
                .filter(e -> e.getAggregateId().value().equals(walletId.value()))
                .sorted(Comparator.comparingLong(Event::getVersion))
                .collect(Collectors.toList());

        if (events.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(Wallet.from(events));
    }
}
