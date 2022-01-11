-- users
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(191) UNIQUE NOT NULL,
    name VARCHAR(191),
    hash_salt VARCHAR(500) NOT NULL,
    roles VARCHAR(16)[]
);