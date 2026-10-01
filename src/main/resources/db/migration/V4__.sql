SELECT
    id,
    product_id,
    quantity,
    reserved_quantity,
    version
FROM inventory
WHERE product_id = 4;

UPDATE inventory
SET
    quantity = 94,
    reserved_quantity = 6
WHERE product_id = 4;