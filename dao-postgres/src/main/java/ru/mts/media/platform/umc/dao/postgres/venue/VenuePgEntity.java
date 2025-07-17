package ru.mts.media.platform.umc.dao.postgres.venue;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mts.media.platform.umc.dao.postgres.common.FullExternalIdPk;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgEntity;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(FullExternalIdPk.class)
@Table(name = "venue",
        indexes = {
                @Index(name = "idx_venue_referenceId",
                        columnList = "referenceId",
                        unique = true)
        })
public class VenuePgEntity {
    @Id
    private String brand;

    @Id
    private String provider;

    @Id
    private String externalId;

    private String referenceId;

    private String name;

    @ManyToMany(mappedBy = "venues")
    private Set<EventPgEntity> events;
}
