package io.axoniq.initializeevents.command.api.events;

import lombok.Data;

@Data
public class BadDataEvent {
    private String id;
    private String badDescription;

    public BadDataEvent(String id, String badDescription) {
        this.id = id;
        this.badDescription = badDescription;
    }
}
