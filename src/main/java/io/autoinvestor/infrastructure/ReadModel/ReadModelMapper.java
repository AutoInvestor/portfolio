package io.autoinvestor.infrastructure.ReadModel;

import io.autoinvestor.application.ComplexReadModelDTO;
import io.autoinvestor.application.SimpleReadModelDTO;
import org.springframework.stereotype.Component;

@Component
public class ReadModelMapper {
    public SimpleReadModelDocument toWalletUserDocument(SimpleReadModelDTO dto) {
        return new SimpleReadModelDocument(
                dto.walletId(),
                dto.userId()
        );
    }

    public ComplexReadModelDocument toComplexDocument(ComplexReadModelDTO dto) {
        return new ComplexReadModelDocument(
                dto.userId(), dto.assetId(), dto.amount(), dto. boughtPrice()
        );
    }

    public SimpleReadModelDTO toDTOSimple(SimpleReadModelDocument document) {
        return new SimpleReadModelDTO(
                document.walletId(),
                document.userId()
        );
    }

    public ComplexReadModelDTO toDTOComplex(ComplexReadModelDocument document) {
        return new ComplexReadModelDTO(
                document.userId(),
                document.assetId(),
                document.amount(),
                document.boughtPrice()
        );
    }
}
