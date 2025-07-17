package ru.mts.media.platform.umc.domain.event;

import ru.mts.media.platform.umc.domain.gql.types.Event;

import java.util.List;
import java.util.Optional;

public interface EventSot {
    Optional<Event> getEventById(Long id);
    List<Event> getAllEvents();
    Event save(Event event);
} 