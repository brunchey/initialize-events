package io.axoniq.initializeevents;

import io.axoniq.initializeevents.command.api.events.RightCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
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

        rightCreated("1", "first one!!!");
        rightCreated("2", "second one!!!");

        eventStore.publish(createdDomainMessages);

    }

    private void rightCreated(String rightId, String description) {
        createdDomainMessages.add(
                createDomainEventMessage(rightId, new RightCreatedEvent(rightId, description))
        );
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
                sequenceNumberDirectory.put(aggregateId, eventStore.lastSequenceNumberFor(aggregateId).orElse(0L));
            } else {
                sequenceNumberDirectory.put(aggregateId, 0L);
            }
        }

        var nextSequenceNumber = sequenceNumberDirectory.get(aggregateId) + 1;
        sequenceNumberDirectory.put(aggregateId, nextSequenceNumber);

        log.info("Next sequence number " + nextSequenceNumber);
        return  nextSequenceNumber;
    }
}


