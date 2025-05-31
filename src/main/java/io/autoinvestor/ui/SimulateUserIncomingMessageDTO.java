package io.autoinvestor.ui;

public record SimulateUserIncomingMessageDTO(
        String eventId,
        String occurredAt,
        String aggregateId,
        String version,
        String type,
        Payload payload) {
    public record Payload(String userId) {}
}
