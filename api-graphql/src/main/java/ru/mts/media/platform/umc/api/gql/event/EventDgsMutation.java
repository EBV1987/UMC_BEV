package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import ru.mts.media.platform.umc.domain.event.EventDomainService;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.EventInput;

/**
 * DGS GraphQL Mutation-резолвер для создания событий (Event).
 * Реализует мутацию createEvent.
 */
@DgsComponent
@RequiredArgsConstructor
public class EventDgsMutation {
    private final EventDomainService eventDomainService;

    /**
     * Создать новое событие (Event) по входным данным.
     * @param input входные данные для создания события
     * @return созданный Event
     */
    @DgsMutation
    public Event createEvent(@InputArgument("input") EventInput input) {
        return eventDomainService.save(input);
    }
} 