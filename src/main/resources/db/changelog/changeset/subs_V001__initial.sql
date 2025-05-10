
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS subscriptions (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT NOT NULL,
    service_name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    status VARCHAR(50) DEFAULT 'inactive',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO users (username, email, password_hash, first_name, last_name)
VALUES
    ('john_doe', 'john.doe@example.com', 'hashed_password_1', 'John', 'Doe'),
    ('jane_smith', 'jane.smith@example.com', 'hashed_password_2', 'Jane', 'Smith'),
    ('alice_williams', 'alice.williams@example.com', 'hashed_password_3', 'Alice', 'Williams'),
    ('bob_johnson', 'bob.johnson@example.com', 'hashed_password_4', 'Bob', 'Johnson');

INSERT INTO subscriptions (user_id, service_name, start_date, end_date, status)
VALUES
    (1, 'Netflix', '2025-01-01', '2026-01-01', 'active'),
    (1, 'YouTube Premium', '2025-03-01', '2025-12-01', 'active'),
    (2, 'VK Музыка', '2025-02-01', '2026-02-01', 'active'),
    (2, 'Yandex Plus', '2025-04-01', '2025-10-01', 'active'),
    (3, 'Netflix', '2025-01-15', '2026-01-15', 'active'),
    (3, 'Spotify', '2025-03-15', NULL, 'active'),
    (4, 'YouTube Premium', '2025-01-20', NULL, 'active'),
    (4, 'Netflix', '2025-02-01', '2025-12-01', 'inactive');