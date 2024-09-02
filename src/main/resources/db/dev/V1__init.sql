CREATE TABLE IF NOT EXISTS restaurant
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(50)    NOT NULL,
    zip_code     VARCHAR(10)    NOT NULL,
    address      VARCHAR(255)   NOT NULL,
    cuisine      VARCHAR(50)    NOT NULL,
    x            DECIMAL(16, 10) NOT NULL,
    y            DECIMAL(16, 10) NOT NULL,
    phone_number VARCHAR(20)    NULL,
    homepage_url VARCHAR(255)   NULL,
    avg_score    DECIMAL(1, 2)  DEFAULT 0.00 NOT NULL,
    view_count   INT            DEFAULT 0 NOT NULL,
    updated_at   TIMESTAMP(6)   NOT NULL,
    UNIQUE (name, address)
    );