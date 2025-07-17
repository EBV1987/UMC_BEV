CREATE TABLE IF NOT EXISTS event (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL
);

COMMENT ON COLUMN event.id IS 'Уникальный идентификатор события';
COMMENT ON COLUMN event.name IS 'Название события';
COMMENT ON COLUMN event.start_time IS 'Дата и время начала события';
COMMENT ON COLUMN event.end_time IS 'Дата и время окончания события';