package ru.mts.media.platform.umc.domain.venue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.SaveVenueInput;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import java.util.function.Function;

/**
 * Сервис бизнес-логики для работы с площадками (Venue).
 * Отвечает за создание, обновление и обработку площадок.
 */
@Service
@RequiredArgsConstructor
public class VenueDomainService {
    private final ApplicationEventPublisher eventPublisher;
    private final VenueSot sot;
    private final VenueDomainServiceMapper mapper;

    /**
     * Сохранить или обновить площадку по идентификатору.
     * @param id составной внешний идентификатор
     * @param input входные данные для обновления
     * @return событие VenueSave с обновлённой площадкой
     */
    public VenueSave save(FullExternalId id, SaveVenueInput input) {
        var evt = sot.getVenueById(id)
                .map(applyPatch(input))
                .map(VenueSave::new)
                .orElse(null);

        eventPublisher.publishEvent(evt);

        return evt;
    }

    private Function<Venue, Venue> applyPatch(SaveVenueInput updates) {
        return x -> mapper.patch(x, updates);
    }
}
