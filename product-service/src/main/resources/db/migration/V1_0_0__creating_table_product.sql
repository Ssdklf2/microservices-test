CREATE TABLE product
(
    product_id  UUID PRIMARY KEY NOT NULL,
    title       VARCHAR(50)      NOT NULL,
    description VARCHAR(255)     NOT NULL,
    price       NUMERIC          NOT NULL CHECK (price > 0)
);