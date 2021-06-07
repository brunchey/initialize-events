package io.axoniq.initializeevents.command.api.events;

import lombok.Data;

@Data
public class RightCreatedEvent {

    private String id;
    private String description;

    public RightCreatedEvent(String id, String description) {
        this.id = id;
        this.description = description;
    }
}
