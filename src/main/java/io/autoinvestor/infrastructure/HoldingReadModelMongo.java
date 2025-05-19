package io.autoinvestor.infrastructure;

import io.autoinvestor.application.HoldingReadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class HoldingReadModelMongo implements HoldingReadModel {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void add(HoldingReadModelDocument document) {
        mongoTemplate.save(document);
    }
}
