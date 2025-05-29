package io.autoinvestor.infrastructure.read_models;

import io.autoinvestor.application.UsersWalletReadModel;
import io.autoinvestor.application.UsersWalletReadModelDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public class MongoUsersWalletReadModel implements UsersWalletReadModel {
    private final MongoTemplate template;
    private final DocumentMapper mapper;

    public MongoUsersWalletReadModel(MongoTemplate template, DocumentMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    @Override
    public void add(UsersWalletReadModelDTO dto) {
        this.template.save(mapper.toDocument(dto));
    }

    @Override
    public String getWalletId(String userId) {
        UsersWalletReadModelDTO dto = template.findById(userId, UsersWalletReadModelDTO.class);

        if (dto != null) {
            return dto.walletId();
        }

        return null;
    }
}
