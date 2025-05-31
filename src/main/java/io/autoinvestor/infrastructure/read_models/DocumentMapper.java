package io.autoinvestor.infrastructure.read_models;

import io.autoinvestor.application.HoldingsReadModelDTO;
import io.autoinvestor.application.UsersWalletReadModelDTO;

import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {
    public MongoUsersWalletReadModelDocument toDocument(UsersWalletReadModelDTO dto) {
        return new MongoUsersWalletReadModelDocument(dto.userId(), dto.walletId());
    }

    public MongoHoldingsReadModelDocument toDocument(HoldingsReadModelDTO dto) {
        return new MongoHoldingsReadModelDocument(
                dto.userId(), dto.assetId(), dto.amount(), dto.boughtPrice());
    }

    public HoldingsReadModelDTO toDTO(MongoHoldingsReadModelDocument document) {
        return new HoldingsReadModelDTO(
                document.userId(), document.assetId(), document.amount(), document.boughtPrice());
    }
}
