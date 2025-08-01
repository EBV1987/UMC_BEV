package ru.mts.media.platform.umc.dao.postgres.venue;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mts.media.platform.umc.dao.postgres.common.FullExternalIdPk;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * MapStruct-мэппер для преобразования между VenuePgEntity (JPA) и Venue (DTO).
 * Игнорирует связанные коллекции events для избежания циклов, наполнение происходит вручную.
 */
@Mapper(componentModel = SPRING)
public interface VenuePgMapper {
    /**
     * Преобразовать JPA-сущность VenuePgEntity в DTO Venue.
     * @param venuePg JPA-сущность
     * @return DTO Venue
     */
    @Mapping(target = "externalId.brandId", source = "brand")
    @Mapping(target = "externalId.providerId", source = "provider")
    @Mapping(target = "externalId.externalId", source = "externalId")
    @Mapping(target = "id", source = "referenceId")
    @Mapping(target = "events", ignore = true)
    Venue asModel(VenuePgEntity venuePg);

    /**
     * Преобразовать DTO Venue в JPA-сущность VenuePgEntity.
     * @param venue DTO Venue
     * @return JPA-сущность
     */
    @Mapping(target = "referenceId", source = "id")
    @Mapping(target = "brand", source = "externalId.brandId")
    @Mapping(target = "provider", source = "externalId.providerId")
    @Mapping(target = "externalId", source = "externalId.externalId")
    @Mapping(target = "events", ignore = true)
    VenuePgEntity asEntity(Venue venue);

    /**
     * Преобразовать FullExternalId в составной ключ FullExternalIdPk.
     * @param fullExternalId DTO идентификатора
     * @return составной ключ
     */
    @Mapping(target = "brand", source = "brandId")
    @Mapping(target = "provider", source = "providerId")
    @Mapping(target = "externalId", source = "externalId")
    FullExternalIdPk asPk(FullExternalId fullExternalId);
}
