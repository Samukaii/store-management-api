UPDATE order_items
SET integration_name = LOWER(
        REGEXP_REPLACE(
                TRANSLATE(integration_name, 'áàãâäéèêëíìîïóòõôöúùûüçÇ', 'aaaaaeeeeiiiiooooouuuucC'), -- Normaliza caracteres especiais
                '[^a-zA-Z0-9 ]', '', 'g'
        )
                       )
WHERE integration_name IS NOT NULL;

UPDATE order_items
SET integration_name = LOWER(REPLACE(integration_name, ' ', '-'))
WHERE integration_name IS NOT NULL;
