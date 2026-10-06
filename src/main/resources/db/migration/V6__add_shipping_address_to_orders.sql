ALTER TABLE orders
    ADD COLUMN shipping_full_name VARCHAR(255),
    ADD COLUMN shipping_street VARCHAR(255),
    ADD COLUMN shipping_city VARCHAR(100),
    ADD COLUMN shipping_state VARCHAR(100),
    ADD COLUMN shipping_postal_code VARCHAR(20),
    ADD COLUMN shipping_country VARCHAR(100),
    ADD COLUMN shipping_phone_number VARCHAR(30);