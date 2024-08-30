CREATE TABLE IF NOT EXISTS member
(
    id        VARCHAR(255) DEFAULT (UUID()) PRIMARY KEY,
    account   VARCHAR(50) UNIQUE NOT NULL,
    email     VARCHAR(100)       NOT NULL,
    password  VARCHAR(255)       NOT NULL,
    joined_at TIMESTAMP(6)       NOT NULL
);