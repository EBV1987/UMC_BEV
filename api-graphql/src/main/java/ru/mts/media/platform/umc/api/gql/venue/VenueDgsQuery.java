package ru.mts.media.platform.umc.api.gql.venue;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.Optional;

/**
 * DGS GraphQL Query-резолвер для получения площадок (Venue) по referenceId.
 * Реализует запрос venueByReferenceId.
 */
@DgsComponent
@RequiredArgsConstructor
public class VenueDgsQuery {
    private final VenueSot sot;

    /**
     * Получить площадку (Venue) по referenceId.
     * @param id referenceId площадки
     * @return Venue или null, если не найдена
     */
    @DgsQuery
    public Venue venueByReferenceId(@InputArgument String id) {
        return Optional.of(id).flatMap(sot::getVenueByReferenceId).orElse(null);
    }
}
