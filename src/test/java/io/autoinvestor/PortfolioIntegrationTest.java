package io.autoinvestor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.autoinvestor.application.*;
import io.autoinvestor.application.NewHoldingUseCase.NewHoldingCommand;
import io.autoinvestor.application.NewHoldingUseCase.NewHoldingCommandHandler;
import io.autoinvestor.application.WalletCreatedUseCase.WalletCreateCommand;
import io.autoinvestor.application.WalletCreatedUseCase.WalletCreatedHandler;
import io.autoinvestor.domain.model.Wallet;
import io.autoinvestor.infrastructure.read_models.InMemoryHoldingsReadModel;
import io.autoinvestor.infrastructure.read_models.InMemoryUsersWalletReadModel;
import io.autoinvestor.infrastructure.repositories.InMemoryEventStoreRepository;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class PortfolioIntegrationTest {

    @Autowired private InMemoryUsersWalletReadModel readModelUserWallet;

    @Autowired private WalletCreatedHandler walletCreatedCommandHandler;

    @Autowired private InMemoryHoldingsReadModel holdingsReadModel;

    @Autowired private NewHoldingCommandHandler newHoldingCommandHandler;

    @Autowired private InMemoryEventStoreRepository eventStore;

    @BeforeEach
    void resetState() {
        readModelUserWallet.clear();
        holdingsReadModel.clear();
    }

    @Test
    void createWalletHandler_shouldCreateWalletForUser() {
        // GIVEN: a user ID that does not exist yet
        String userId = "user-1";

        // PRECONDITION: portfolioRepository has no wallet for "user-1"
        assertThat(readModelUserWallet.getWalletId(userId)).isNull();

        // WHEN: we invoke the CreateWalletCommandHandler with the id of the user
        walletCreatedCommandHandler.handle(new WalletCreateCommand(userId));

        // THEN: the portfolioRepository should now contain a wallet for "user-1"
        assertThat(readModelUserWallet.getWalletId(userId)).isNotEmpty();
    }

    @Test
    void createAsset_shouldCreateAssetForUser() {
        String userId = "user-1";
        String walletId = "wallet-1";
        String assetId = "asset-id";
        int amount = 1;
        int boughtPrice = 1;

        // Add wallet to usersWalletReadModel

        // Simulate wallet creation event and save it in the event store
        Wallet wallet = Wallet.create(userId);
        // Wallet.create generates WalletWasCreatedEvent inside

        readModelUserWallet.add(
                new UsersWalletReadModelDTO(wallet.getState().getWalletIdString(), userId));
        // Save the wallet creation event(s) in the event store
        eventStore.save(wallet);

        // Now asset should NOT exist yet
        assertThat(holdingsReadModel.assetAlreadyExists(userId, assetId)).isFalse();

        // Call your handler
        newHoldingCommandHandler.handle(
                new NewHoldingCommand(assetId, userId, amount, boughtPrice));

        // Verify the holding is added
        Collection<HoldingsReadModelDTO> holdingsOfUser = holdingsReadModel.getHoldings(userId);
        assertThat(holdingsOfUser).extracting(HoldingsReadModelDTO::assetId).contains(assetId);
    }
}
