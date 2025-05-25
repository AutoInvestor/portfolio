package io.autoinvestor.infrastructure.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PubSubEventMapper {

    private final ObjectMapper mapper =
            new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    public PubSubEvent fromMap(Map<String, ?> raw) {
        return mapper.convertValue(raw, PubSubEvent.class);
    }
}