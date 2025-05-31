package io.autoinvestor.infrastructure.repositories;

import io.autoinvestor.domain.events.Event;
import io.autoinvestor.domain.events.WalletEventStoreRepository;
import io.autoinvestor.domain.model.Wallet;
import io.autoinvestor.domain.model.WalletId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public class MongoEventStoreRepository implements WalletEventStoreRepository {
    private static final String COLLECTION = "events";

    private final MongoTemplate template;
    private final EventMapper mapper;

    public MongoEventStoreRepository(MongoTemplate template, EventMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    @Override
    public void save(Wallet wallet) {
        List<EventDocument> docs =
                wallet.getUncommittedEvents().stream()
                        .map(mapper::toDocument)
                        .collect(Collectors.toList());
        template.insertAll(docs);
    }

    @Override
    public Optional<Wallet> get(WalletId walletId) {
        Query q =
                Query.query(Criteria.where("aggregateId").is(walletId.value()))
                        .with(Sort.by("version"));

        List<EventDocument> docs = template.find(q, EventDocument.class, COLLECTION);

        if (docs.isEmpty()) {
            return Optional.empty();
        }

        List<Event<?>> events = docs.stream().map(mapper::toDomain).collect(Collectors.toList());

        if (events.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(Wallet.from(events));
    }
}
