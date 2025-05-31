package io.autoinvestor.infrastructure.listeners;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class PubSubEventMapper {
    private final ObjectMapper mapper;

    public PubSubEvent fromMap(Map<String, ?> raw) {
        return mapper.convertValue(raw, PubSubEvent.class);
    }
}
