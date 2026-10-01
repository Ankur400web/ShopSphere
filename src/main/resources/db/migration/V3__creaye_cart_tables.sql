CREATE TABLE carts (
       id BIGSERIAL PRIMARY KEY,
       user_id BIGINT NOT NULL,
       created_at TIMESTAMPTZ NOT NULL DEFAULT current_timestamp,
       updated_at TIMESTAMPTZ NOT NULL DEFAULT current_timestamp,

       CONSTRAINT fk_cart_user
           FOREIGN KEY (user_id)
               REFERENCES users(id)
               ON DELETE RESTRICT,

       CONSTRAINT uk_cart_user
           UNIQUE (user_id)
);

CREATE TABLE cart_items (
        id BIGSERIAL PRIMARY KEY,
        cart_id BIGINT NOT NULL,
        product_id BIGINT NOT NULL,
        quantity INT NOT NULL,
        created_at TIMESTAMPTZ NOT NULL DEFAULT current_timestamp,
        updated_at TIMESTAMPTZ NOT NULL DEFAULT current_timestamp,

        CONSTRAINT chk_cart_item_quantity
            CHECK (quantity > 0),

        CONSTRAINT fk_cart_item_cart
            FOREIGN KEY (cart_id)
                REFERENCES carts(id)
                ON DELETE RESTRICT,

        CONSTRAINT fk_cart_item_product
            FOREIGN KEY (product_id)
                REFERENCES products(id)
                ON DELETE RESTRICT,

        CONSTRAINT uk_cart_product
            UNIQUE (cart_id, product_id)
);