package ru.mts.media.platform.umc.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.EventInput;

import java.util.List;

/**
 * Сервис бизнес-логики для работы с событиями (Event).
 * Отвечает за создание, получение и обработку событий.
 */
@Service
@RequiredArgsConstructor
public class EventDomainService {
    private final EventSot sot;
    private final EventDomainServiceMapper mapper;

    /**
     * Сохранить событие на основе входных данных.
     * @param input входные данные для создания/обновления события
     * @return сохранённый Event
     */
    public Event save(EventInput input) {
        var event = mapper.fromInput(input);
        var saved = sot.save(event);
        return saved;
    }

    /**
     * Получить все события.
     * @return список событий
     */
    public List<Event> getAllEvents() {
        return sot.getAllEvents();
    }
} 