package ru.mts.media.platform.umc.dao.postgres.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgEntity;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "event")
public class EventPgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToMany
    @JoinTable(
            name = "event_venue",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "venue_brand", referencedColumnName = "brand"),
                    @JoinColumn(name = "venue_provider", referencedColumnName = "provider"),
                    @JoinColumn(name = "venue_external_id", referencedColumnName = "externalId")
            }
    )
    private Set<VenuePgEntity> venues;
} 