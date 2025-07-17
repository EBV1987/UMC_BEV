DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.tables WHERE table_name = 'event'
    ) AND EXISTS (
        SELECT 1 FROM information_schema.tables WHERE table_name = 'venue'
    ) THEN
        CREATE TABLE IF NOT EXISTS event_venue (
            event_id BIGINT NOT NULL,
            venue_brand VARCHAR(255) NOT NULL,
            venue_provider VARCHAR(255) NOT NULL,
            venue_external_id VARCHAR(255) NOT NULL,
            PRIMARY KEY (event_id, venue_brand, venue_provider, venue_external_id),
            FOREIGN KEY (event_id) REFERENCES event(id),
            FOREIGN KEY (venue_brand, venue_provider, venue_external_id) REFERENCES venue(brand, provider, external_id)
        );
        COMMENT ON COLUMN event_venue.event_id IS 'Внешний ключ на event.id';
        COMMENT ON COLUMN event_venue.venue_brand IS 'Внешний ключ на venue.brand';
        COMMENT ON COLUMN event_venue.venue_provider IS 'Внешний ключ на venue.provider';
        COMMENT ON COLUMN event_venue.venue_external_id IS 'Внешний ключ на venue.external_id';
        COMMENT ON CONSTRAINT event_venue_pkey ON event_venue IS 'Составной первичный ключ';
    END IF;
END $$;