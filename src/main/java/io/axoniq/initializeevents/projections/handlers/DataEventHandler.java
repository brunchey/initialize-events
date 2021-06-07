package io.axoniq.initializeevents.projections.handlers;

import io.axoniq.initializeevents.command.api.events.RightCreatedEvent;
import lombok.extern.java.Log;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Log
@Component
public class DataEventHandler {


    @EventHandler
    public void handle(RightCreatedEvent event) {
        log.info("Received ");
    }

}
