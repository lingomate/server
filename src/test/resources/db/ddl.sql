CREATE TABLE IF NOT EXISTS users
(
    id                      BIGSERIAL               PRIMARY KEY,
    username                VARCHAR(32)             NOT NULL,
    UNIQUE (username),
    hash_salt               VARCHAR(500)            NOT NULL
);