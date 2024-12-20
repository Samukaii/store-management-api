CREATE TABLE preparations
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name     VARCHAR(255),
    cost     DOUBLE PRECISION,
    quantity DOUBLE PRECISION,
    CONSTRAINT pk_preparations PRIMARY KEY (id)
);

ALTER TABLE food_inputs
    ADD preparation_id BIGINT;

ALTER TABLE food_inputs
    ADD CONSTRAINT FK_FOOD_INPUTS_ON_PREPARATION FOREIGN KEY (preparation_id) REFERENCES preparations (id);

ALTER TABLE products
    ALTER COLUMN profit DROP NOT NULL;

ALTER TABLE products
    ALTER COLUMN profit_margin DROP NOT NULL;

ALTER TABLE products
    ALTER COLUMN suggested_price DROP NOT NULL;

ALTER TABLE products
    ALTER COLUMN total_cost DROP NOT NULL;