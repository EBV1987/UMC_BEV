package ru.mts.media.platform.umc.domain.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.EventInput;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import java.util.List;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * MapStruct-мэппер для преобразования входных данных EventInput в Event (DTO).
 * Использует кастомный метод для преобразования списка venueReferenceIds в список Venue.
 */
@Mapper(componentModel = SPRING)
public interface EventDomainServiceMapper {
    /**
     * Преобразовать входные данные EventInput в Event.
     * @param input входные данные
     * @return DTO Event
     */
    @Mapping(target = "venues", expression = "java(toVenues(input.getVenueReferenceIds()))")
    Event fromInput(EventInput input);

    /**
     * Преобразовать список referenceId в список Venue с заполненным id.
     * @param referenceIds список referenceId
     * @return список Venue с заполненным id
     */
    default List<Venue> toVenues(List<String> referenceIds) {
        if (referenceIds == null) return java.util.Collections.emptyList();
        return referenceIds.stream().map(id ->
            Venue.newBuilder().id(id).build()
        ).collect(Collectors.toList());
    }
} 