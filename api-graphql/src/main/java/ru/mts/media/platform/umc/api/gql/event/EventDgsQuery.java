package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import ru.mts.media.platform.umc.domain.event.EventDomainService;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DGS GraphQL Query-резолвер для работы с событиями (Event) и площадками (Venue).
 * Реализует запросы событий и площадок с событиями.
 */
@DgsComponent
@RequiredArgsConstructor
public class EventDgsQuery {
    private final EventDomainService eventDomainService;
    private final VenueSot venueSot;

    /**
     * Получить все события.
     * @return список событий
     */
    @DgsQuery
    public List<Event> events() {
        return eventDomainService.getAllEvents();
    }

    /**
     * Получить все площадки, на которых есть события (venuesWithEvents).
     * Возвращает полные объекты Venue с наполненным полем events.
     * @return список площадок с событиями
     */
    @DgsQuery
    public List<Venue> venuesWithEvents() {
        // Получаем все уникальные venue referenceId из событий
        List<String> venueIds = eventDomainService.getAllEvents().stream()
            .flatMap(e -> e.getVenues() != null ? e.getVenues().stream() : java.util.stream.Stream.empty())
            .map(Venue::getId)
            .distinct()
            .collect(Collectors.toList());
        
        // Получаем полные объекты Venue через VenueSot
        return venueIds.stream()
            .map(id -> venueSot.getVenueByReferenceId(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
} 