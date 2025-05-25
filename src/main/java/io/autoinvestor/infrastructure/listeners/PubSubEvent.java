package io.autoinvestor.infrastructure.listeners;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PubSubEvent {

    private String eventId;

    private long occurredAt;

    private String aggregateId;

    private int version;

    private String type;

    private Map<String, Object> payload;
}

