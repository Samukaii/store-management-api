ALTER TABLE product_food_inputs
    ADD preparation_id BIGINT;

ALTER TABLE product_food_inputs
    ADD CONSTRAINT FK_PRODUCT_FOOD_INPUTS_ON_PREPARATION FOREIGN KEY (preparation_id) REFERENCES preparations (id);

ALTER TABLE product_food_inputs
    ALTER COLUMN quantity TYPE DOUBLE PRECISION;
