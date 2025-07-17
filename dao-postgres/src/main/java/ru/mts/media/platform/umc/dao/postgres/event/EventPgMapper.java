package ru.mts.media.platform.umc.dao.postgres.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mts.media.platform.umc.domain.gql.types.Event;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * MapStruct-мэппер для преобразования между EventPgEntity (JPA) и Event (DTO).
 * Игнорирует связанные коллекции venues для избежания циклов, наполнение происходит вручную.
 */
@Mapper(componentModel = SPRING)
public interface EventPgMapper {
    /**
     * Преобразовать JPA-сущность EventPgEntity в DTO Event.
     * @param entity JPA-сущность
     * @return DTO Event
     */
    @Mapping(target = "venues", ignore = true) // обработка связи отдельно
    Event asModel(EventPgEntity entity);

    /**
     * Преобразовать DTO Event в JPA-сущность EventPgEntity.
     * @param event DTO Event
     * @return JPA-сущность
     */
    @Mapping(target = "venues", ignore = true)
    EventPgEntity asEntity(Event event);

}