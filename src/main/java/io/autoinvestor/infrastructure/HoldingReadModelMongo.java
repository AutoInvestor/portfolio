package io.autoinvestor.infrastructure;

import io.autoinvestor.application.HoldingReadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class HoldingReadModelMongo implements HoldingReadModel {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void add(HoldingReadModelDocument document) {
        mongoTemplate.save(document);
    }

    @Override
    public List<HoldingReadModelDocument> get(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, HoldingReadModelDocument.class);
    }
}
