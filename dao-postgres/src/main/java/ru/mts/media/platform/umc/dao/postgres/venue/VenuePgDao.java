package ru.mts.media.platform.umc.dao.postgres.venue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgMapper;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgRepository;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueSave;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DAO для работы с сущностью Venue в Postgres.
 * Отвечает за преобразование между JPA и DTO, ручное наполнение связанных Event.
 * Используется в VenueSot.
 */
@Component
@RequiredArgsConstructor
class VenuePgDao implements VenueSot {
    private final VenuePgRepository repository;
    private final VenuePgMapper mapper;
    private final EventPgRepository eventRepository;
    private final EventPgMapper eventMapper;

    /**
     * Получить площадку по referenceId с наполнением связанных Event.
     * @param id referenceId площадки
     * @return Optional с Venue, если найдена
     */
    public Optional<Venue> getVenueByReferenceId(String id) {
        return Optional.of(id)
                .map(repository::findByReferenceId)
                .map(entity -> {
                    Venue venue = mapper.asModel(entity);
                    if (entity.getEvents() != null) {
                        List<Event> events = entity.getEvents().stream()
                                .filter(Objects::nonNull)
                                .map(eventMapper::asModel)
                                .collect(Collectors.toList());
                        venue.setEvents(events);
                    } else {
                        venue.setEvents(List.of());
                    }
                    return venue;
                });
    }

    /**
     * Получить площадку по составному внешнему идентификатору с наполнением связанных Event.
     * @param externalId составной внешний идентификатор
     * @return Optional с Venue, если найдена
     */
    @Override
    public Optional<Venue> getVenueById(FullExternalId externalId) {
        return Optional.of(externalId)
                .map(mapper::asPk)
                .flatMap(repository::findById)
                .map(entity -> {
                    Venue venue = mapper.asModel(entity);
                    if (entity.getEvents() != null) {
                        List<Event> events = entity.getEvents().stream()
                                .filter(Objects::nonNull)
                                .map(eventMapper::asModel)
                                .collect(Collectors.toList());
                        venue.setEvents(events);
                    } else {
                        venue.setEvents(List.of());
                    }
                    return venue;
                });
    }

    /**
     * Обработчик создания новой площадки (Venue) через событие.
     * @param evt событие создания Venue
     */
    @EventListener
    public void handleVenueCreatedEvent(VenueSave evt) {
        evt.unwrap()
                .map(venue -> {
                    var entity = mapper.asEntity(venue);
                    // При создании venue событий ещё нет, поэтому events = null
                    entity.setEvents(null);
                    return entity;
                })
                .ifPresent(repository::save);
    }
}
