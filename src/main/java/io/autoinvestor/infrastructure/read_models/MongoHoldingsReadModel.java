package io.autoinvestor.infrastructure.read_models;

import com.mongodb.client.result.DeleteResult;
import io.autoinvestor.application.HoldingsReadModel;
import io.autoinvestor.application.HoldingsReadModelDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("prod")
public class MongoHoldingsReadModel implements HoldingsReadModel {
    private final MongoTemplate template;
    private final DocumentMapper mapper;

    public MongoHoldingsReadModel(MongoTemplate template, DocumentMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    @Override
    public void add(HoldingsReadModelDTO dto) {
        this.template.save(mapper.toDocument(dto));
    }

    @Override
    public void update(HoldingsReadModelDTO dto) {
        Query query = new Query(Criteria.where("userId").is(dto.userId())
                .and("assetId").is(dto.assetId()));

        Update update = new Update()
                .set("amount", dto.amount())
                .set("boughtPrice", dto.boughtPrice());

        this.template.updateFirst(query, update, MongoHoldingsReadModelDocument.class);
    }

    @Override
    public boolean delete(String userId, String assetId) {
        Query query = new Query(Criteria.where("userId").is(userId)
                .and("assetId").is(assetId));
        DeleteResult result = this.template.remove(query, MongoHoldingsReadModelDocument.class);
        return result.getDeletedCount() > 0;
    }

    @Override
    public List<HoldingsReadModelDTO> getHoldings(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return this.template.find(query, MongoHoldingsReadModelDocument.class)
                .stream().map(mapper::toDTO).toList();
    }
}
