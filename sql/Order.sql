CREATE TABLE orders
(
    id           SERIAL PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL,
    total_amount INTEGER     NOT NULL,
    status       VARCHAR(30) NOT NULL,
    created_at   DATE        NOT NULL
);