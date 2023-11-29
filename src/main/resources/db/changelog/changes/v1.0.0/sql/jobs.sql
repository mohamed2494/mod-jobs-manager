
CREATE TABLE jobs
(
    id          UUID NOT NULL PRIMARY KEY,
    type        VARCHAR(50) NOT NULL,
    status      VARCHAR(50) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payload     JSONB
);
