package io.autoinvestor.infrastructure;

import io.autoinvestor.domain.Event;
import io.autoinvestor.domain.Wallet;
import io.autoinvestor.domain.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Primary
@RequiredArgsConstructor
public class MongoWalletRepository implements WalletRepository {
    private final MongoTemplate template;
    private final HoldingEventMapperDocument mapper;


    @Override
    public void save(List<Event<?>> walletEvents) {
        List<WalletEventDocument> documents = walletEvents.stream()
                .map(mapper::toDocument)
                .collect(Collectors.toList());
        template.insertAll(documents);
    }

   @Override
    public Wallet get(String walletId) {
        Query query = new Query(Criteria.where("walletId").is(walletId));
        query.with(Sort.by(Sort.Direction.ASC, "version"));
        List<WalletEventDocument> documents = template.find(query, WalletEventDocument.class);
        if (documents.isEmpty()) {
            return null;
        }
       List<Event<?>> events = documents.stream()
               .map(mapper::toDomainEvent)
               .collect(Collectors.toList());
        if (events.isEmpty()) {
            return Wallet.empty();
        }
        return Wallet.from(events);

    }
}
