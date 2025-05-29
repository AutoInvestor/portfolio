package io.autoinvestor.infrastructure.read_models;

import io.autoinvestor.application.HoldingsReadModelDTO;
import io.autoinvestor.application.UsersWalletReadModelDTO;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {
    public MongoUsersWalletReadModelDocument toDocument(UsersWalletReadModelDTO dto) {
        return new MongoUsersWalletReadModelDocument(
                dto.walletId(),
                dto.userId()
        );
    }

    public MongoHoldingsReadModelDocument toDocument(HoldingsReadModelDTO dto) {
        return new MongoHoldingsReadModelDocument(
                dto.userId(), dto.assetId(), dto.amount(), dto. boughtPrice()
        );
    }

    public UsersWalletReadModelDTO toDTO(MongoUsersWalletReadModelDocument document) {
        return new UsersWalletReadModelDTO(
                document.walletId(),
                document.userId()
        );
    }

    public HoldingsReadModelDTO toDTO(MongoHoldingsReadModelDocument document) {
        return new HoldingsReadModelDTO(
                document.userId(),
                document.assetId(),
                document.amount(),
                document.boughtPrice()
        );
    }
}
