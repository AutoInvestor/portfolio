package io.autoinvestor.infrastructure;

import io.autoinvestor.domain.Event;
import io.autoinvestor.domain.HoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Primary
@RequiredArgsConstructor
public class MongoHoldingRepository implements HoldingRepository {
    private final MongoTemplate template;
    private final AddHoldingEventMapperDocument mapper;


    @Override
    public void save(List<Event<?>> holdingEvents) {
        List<HoldingAddedEventDocument> documents = holdingEvents.stream()
                .map(mapper::toDocument)
                .collect(Collectors.toList());
        template.insertAll(documents);
    }
}
