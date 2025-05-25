package io.autoinvestor.infrastructure;

import io.autoinvestor.domain.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HoldingEventMapperDocument {
    WalletEventDocument toDocument(Event<?> walletEvent){
        switch (walletEvent.getType()) {
            case "WALLET_CREATED" -> {
                WalletWasCreatedPayload payloadAdded = (WalletWasCreatedPayload) walletEvent.getPayload();

                return new WalletEventDocument(
                        walletEvent.getId().value(),
                        walletEvent.getType(),
                        walletEvent.getAggregateId().value(),
                        payloadAdded.userId().value(),
                        null,
                        null,
                        null,
                        walletEvent.getOccurredAt().toInstant(),
                        walletEvent.getVersion()
                );
            }
            case "PORTFOLIO_ASSET_ADDED" -> {
                HoldingWasCreatedEventPayload payload = (HoldingWasCreatedEventPayload) walletEvent.getPayload();
                return new WalletEventDocument(
                        walletEvent.getId().value(),
                        walletEvent.getType(),
                        walletEvent.getAggregateId().value(),
                        payload.userId().value(),
                        payload.assetId().value(),
                        payload.amount().value(),
                        payload.boughtPrice().value(),
                        walletEvent.getOccurredAt().toInstant(),
                        walletEvent.getVersion()
                );
            }
            case "PORTFOLIO_ASSET_UPDATED" -> {
                HoldingWasUpdatedEventPayload payload = (HoldingWasUpdatedEventPayload) walletEvent.getPayload();
                return new WalletEventDocument(
                        walletEvent.getId().value(),
                        walletEvent.getType(),
                        walletEvent.getAggregateId().value(),
                        payload.userId().value(),
                        payload.assetId().value(),
                        payload.amount().value(),
                        payload.boughtPrice().value(),
                        walletEvent.getOccurredAt().toInstant(),
                        walletEvent.getVersion()
                );
            }
            default -> throw new IllegalArgumentException ("Unknown event type " + walletEvent.getType());
        }
    }
    public Event<?> toDomainEvent(WalletEventDocument doc) {

        WalletId walletId = WalletId.of(doc.walletId());
        Date occurredAt = Date.from(doc.ocurredAt());
        int version = doc.version();
        switch (doc.eventType()) {
            case "WALLET_CREATED" -> {
                WalletWasCreatedPayload payload = new WalletWasCreatedPayload(UserId.of(doc.userId()));

                return WalletWasCreatedEvent.hydrate(
                        walletId,
                        payload,
                        version,
                        occurredAt
                );
            }
            case "PORTFOLIO_ASSET_ADDED" -> {
                HoldingWasCreatedEventPayload payload = new HoldingWasCreatedEventPayload(
                        UserId.of(doc.userId()),
                        AssetId.of(doc.assetId()),
                        new Amount(doc.amount()),
                        new BoughtPrice(doc.boughtPrice())
                );
                return HoldingWasCreatedEvent.hydrate(
                        walletId,
                        payload,
                        version,
                        occurredAt
                );
            }
            case "PORTFOLIO_ASSET_UPDATED" -> {
                HoldingWasUpdatedEventPayload payload = new HoldingWasUpdatedEventPayload(
                        UserId.of(doc.userId()),
                        AssetId.of(doc.assetId()),
                        new Amount(doc.amount()),
                        new BoughtPrice(doc.boughtPrice())
                );
                return HoldingWasUpdatedEvent.hydrate(
                        walletId,
                        payload,
                        version,
                        occurredAt
                );
            }
            default -> throw new IllegalArgumentException("Unknown event type: " + doc.eventType());
        }

    }
}
