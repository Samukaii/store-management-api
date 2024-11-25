ALTER TABLE product_food_inputs
    ADD ingredient_type SMALLINT;

ALTER TABLE product_food_inputs
    DROP COLUMN measurement_unit;