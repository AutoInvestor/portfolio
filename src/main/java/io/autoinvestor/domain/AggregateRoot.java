package io.autoinvestor.domain;

import java.util.ArrayList;
import java.util.List;

public class AggregateRoot {
    private final List<Event<?>> appliedEvents;
    private Integer version;

    protected AggregateRoot(List<Event<?>> stream) {
        if (!stream.isEmpty()) {
            for (Event<?> event : stream) {
                this.when(event);
            }
            this.version = stream.size();
        } else {
            this.version = 0;
        }
        appliedEvents = new ArrayList<>();
    }

    protected void when(Event<?> event) {
    }

    protected void recordEvent(Event<? extends EventPayload> event) {
        this.appliedEvents.add(event);
    }

    protected void apply(Event<?> event) {
        this.when(event);
        this.appliedEvents.add(event);
    }

    public List<Event<?>> releaseEvents() {
        List<Event<?>> events = new ArrayList<>(this.appliedEvents);
        this.appliedEvents.clear();
        return events;
    }
}
