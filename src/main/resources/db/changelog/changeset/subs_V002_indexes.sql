CREATE INDEX idx_users_email ON users(email);

CREATE INDEX idx_users_username ON users(username);

CREATE INDEX idx_subscriptions_user_id ON subscriptions(user_id);

CREATE INDEX idx_subscriptions_service_name ON subscriptions(service_name);
