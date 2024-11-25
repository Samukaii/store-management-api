ALTER TABLE product_food_inputs RENAME TO product_ingredients;

ALTER TABLE product_ingredients RENAME food_input_id TO raw_material_id;
