package ru.mts.media.platform.umc.dao.postgres.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgEntity;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgMapper;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgRepository;
import ru.mts.media.platform.umc.domain.event.EventSot;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DAO для работы с сущностью Event в Postgres.
 * Отвечает за преобразование между JPA и DTO, ручное наполнение связанных Venue.
 * Используется в EventSot.
 */
@Component
@RequiredArgsConstructor
public class EventPgDao implements EventSot {
    private final EventPgRepository repository;
    private final EventPgMapper mapper;
    private final VenuePgRepository venueRepository;
    private final VenuePgMapper venueMapper;

    /**
     * Получить событие по идентификатору с наполнением связанных Venue.
     * @param id идентификатор события
     * @return Optional с Event, если найден
     */
    @Override
    public Optional<Event> getEventById(Long id) {
        return repository.findById(id).map(entity -> {
            Event event = mapper.asModel(entity);
            if (entity.getVenues() != null) {
                List<Venue> venues = entity.getVenues().stream()
                    .map(v -> venueRepository.findByReferenceId(v.getReferenceId()))
                    .filter(java.util.Objects::nonNull)
                    .map(venueMapper::asModel)
                    .collect(Collectors.toList());
                event.setVenues(venues);
            } else {
                event.setVenues(List.of());
            }
            return event;
        });
    }

    /**
     * Получить все события из БД с наполнением связанных Venue.
     * @return список событий с заполненными площадками
     */
    @Override
    public List<Event> getAllEvents() {
        return repository.findAll().stream().map(entity -> {
            Event event = mapper.asModel(entity);
            if (entity.getVenues() != null) {
                List<Venue> venues = entity.getVenues().stream()
                    .map(v -> venueRepository.findByReferenceId(v.getReferenceId()))
                    .filter(java.util.Objects::nonNull)
                    .map(venueMapper::asModel)
                    .collect(Collectors.toList());
                event.setVenues(venues);
            } else {
                event.setVenues(List.of());
            }
            return event;
        }).toList();
    }

    /**
     * Сохранить событие в БД с установкой связей с Venue.
     * @param event DTO события
     * @return сохранённый Event
     */
    @Override
    public Event save(Event event) {
        Set<VenuePgEntity> venues = event.getVenues() == null ? Set.of() :
            event.getVenues().stream()
                .map(v -> venueRepository.findByReferenceId(v.getId()))
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());
        var entity = mapper.asEntity(event);
        entity.setVenues(venues);
        var saved = repository.save(entity);
        return mapper.asModel(saved);
    }
} 