CREATE TABLE customer
(
    customer_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    age         INT          NOT NULL,
    email       VARCHAR(100) NOT NULL,
    city        VARCHAR(50)
);