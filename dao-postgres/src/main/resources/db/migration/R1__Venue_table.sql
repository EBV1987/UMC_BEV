CREATE TABLE IF NOT EXISTS venue
(
    brand        VARCHAR(255) NOT NULL,
    provider     VARCHAR(255) NOT NULL,
    external_id  VARCHAR(255) NOT NULL,
    reference_id VARCHAR(255) NOT NULL,
    name         VARCHAR(255) NOT NULL,
    PRIMARY KEY (brand, provider, external_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_venue_reference_id ON venue(reference_id);

COMMENT ON COLUMN venue.brand IS 'Бренд площадки (часть составного ключа)';
COMMENT ON COLUMN venue.provider IS 'Провайдер площадки (часть составного ключа)';
COMMENT ON COLUMN venue.external_id IS 'Внешний идентификатор площадки (часть составного ключа)';
COMMENT ON COLUMN venue.reference_id IS 'Уникальный референс площадки (используется для поиска)';
COMMENT ON COLUMN venue.name IS 'Название площадки';
COMMENT ON CONSTRAINT venue_pkey ON venue IS 'Составной первичный ключ';
COMMENT ON INDEX idx_venue_reference_id IS 'Уникальный индекс по reference_id';