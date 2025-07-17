package ru.mts.media.platform.umc.domain.venue;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.mts.media.platform.umc.domain.gql.types.SaveVenueInput;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * MapStruct-мэппер для обновления (patch) данных Venue на основе входных данных SaveVenueInput.
 */
@Mapper(componentModel = SPRING)
interface VenueDomainServiceMapper {
    /**
     * Обновить (patch) данные Venue на основе SaveVenueInput.
     * @param src исходный Venue
     * @param updates входные данные для обновления
     * @return обновлённый Venue
     */
    Venue patch(@MappingTarget Venue src, SaveVenueInput updates);
}
