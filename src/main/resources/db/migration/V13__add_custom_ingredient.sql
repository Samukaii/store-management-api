ALTER TABLE preparation_ingredients
    ADD custom_cost DOUBLE PRECISION;

ALTER TABLE preparation_ingredients
    ADD custom_name VARCHAR(255);

ALTER TABLE preparation_ingredients
    ADD ingredient_type SMALLINT;

ALTER TABLE product_food_inputs
    RENAME additional_cost TO custom_cost;

ALTER TABLE product_food_inputs
    RENAME name TO custom_name;
