package io.axoniq.initializeevents;

import io.axoniq.initializeevents.command.api.events.BadDataEvent;
import io.axoniq.initializeevents.command.api.events.RightCreatedEvent;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ImportEvents implements CommandLineRunner {

    private final EventStore eventStore;
    private final Map<String, Long> sequenceNumberDirectory = new HashMap<>();
    private final List<DomainEventMessage<?>> createdDomainMessages = new ArrayList<>();

    public ImportEvents(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void run(String... args) throws Exception {

        //profile for generating events

        rightCreated("1", "first one!!!");
        rightCreated("2", "second one!!!");
        badCreated("1");
        badCreated("2");


        eventStore.publish(createdDomainMessages);

        //create another profile for consuming events
    }

    private void rightCreated(String rightId, String description) {
        createdDomainMessages.add(
                createDomainEventMessage(rightId, new RightCreatedEvent(rightId, description))
        );
    }

    private void badCreated(String aggregateId) {
        createdDomainMessages.add(
                createDomainEventMessage(aggregateId,
                                    RightCreatedEvent.class,
                                    new BadDataEvent(aggregateId, String.format("second event for aggregateId %s ", aggregateId ))
        ));
    }

    private <P> DomainEventMessage<P> createDomainEventMessage(String aggregateId, Class payloadType, P payload) {
        return new GenericDomainEventMessage<>(payloadType.getName(),
                aggregateId,
                getNextSequenceNumber(aggregateId),
                payload);
    }


    private <P> DomainEventMessage<P> createDomainEventMessage(String aggregateId, P payload) {
        return new GenericDomainEventMessage<>(payload.getClass().getName(),
                aggregateId,
                getNextSequenceNumber(aggregateId),
                payload);
    }

    private long getNextSequenceNumber(String aggregateId) {
        if (sequenceNumberDirectory.get(aggregateId) == null) {
            if (eventStore != null) {
                sequenceNumberDirectory.put(aggregateId, Optional.ofNullable(eventStore.readEvents(aggregateId).getLastSequenceNumber()).orElse(0L));
            } else {
                sequenceNumberDirectory.put(aggregateId, 0L);
            }
        }

        return sequenceNumberDirectory.put(aggregateId, sequenceNumberDirectory.get(aggregateId) + 1);

    }
}


