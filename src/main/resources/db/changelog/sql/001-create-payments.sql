CREATE TABLE payments
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT         NOT NULL,
    amount     NUMERIC(18, 2) NOT NULL,
    status     VARCHAR(30)    NOT NULL,
    created_at TIMESTAMP      NOT NULL,
    updated_at TIMESTAMP      NOT NULL
);

CREATE INDEX idx_payments_user_id ON payments (user_id);