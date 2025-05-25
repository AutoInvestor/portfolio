package io.autoinvestor.infrastructure.ReadModel;

import io.autoinvestor.application.ComplexReadModelDTO;
import io.autoinvestor.application.ReadModel;
import io.autoinvestor.application.SimpleReadModelDTO;
import io.autoinvestor.domain.Holding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Primary
public class ReadModelMongo implements ReadModel{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ReadModelMapper mapper;

    @Override
    public void add(SimpleReadModelDTO dto) {
        mongoTemplate.save(mapper.toWalletUserDocument(dto));
    }

    @Override
    public void add(ComplexReadModelDTO dto) {
        mongoTemplate.save(mapper.toComplexDocument(dto));
    }

    @Override
    public String getWalletId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        String walletId = mongoTemplate.findOne(query, SimpleReadModelDocument.class).walletId();
        return walletId;


}

    @Override
    public void update(ComplexReadModelDTO dto) {
        Query query = new Query(Criteria.where("userId").is(dto.userId())
                .and("assetId").is(dto.assetId()));
        Update update = new Update()
                .set("amount", dto.amount())
                .set("boughtPrice", dto.boughtPrice());
        mongoTemplate.updateFirst(query, update, ComplexReadModelDocument.class);
    }

    @Override
    public List<ComplexReadModelDTO>  getHoldings(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, ComplexReadModelDocument.class)
                .stream().map(mapper::toDTOComplex).toList();
    }
}

