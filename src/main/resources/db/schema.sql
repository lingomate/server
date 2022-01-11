-- users
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    name TEXT,
    hash_salt VARCHAR(500) NOT NULL,
    roles TEXT[]
);