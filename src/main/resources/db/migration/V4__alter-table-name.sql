ALTER TABLE food_inputs RENAME TO raw_materials;

ALTER TABLE preparations
    RENAME COLUMN cost TO total_cost;
