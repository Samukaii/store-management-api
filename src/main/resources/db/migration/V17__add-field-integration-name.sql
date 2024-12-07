ALTER TABLE order_items
    ADD COLUMN integration_name VARCHAR(255);

UPDATE order_items SET integration_name = name WHERE integration_name IS NULL;
