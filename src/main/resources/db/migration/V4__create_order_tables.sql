CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,

                        user_id BIGINT NOT NULL,

                        status VARCHAR(30) NOT NULL,

                        subtotal NUMERIC(19, 2) NOT NULL,
                        shipping_amount NUMERIC(19, 2) NOT NULL,
                        total_amount NUMERIC(19, 2) NOT NULL,

                        created_at TIMESTAMPTZ NOT NULL,
                        updated_at TIMESTAMPTZ NOT NULL,

                        CONSTRAINT fk_orders_user
                            FOREIGN KEY (user_id)
                                REFERENCES users(id)
);

CREATE TABLE order_items (
                             id BIGSERIAL PRIMARY KEY,

                             order_id BIGINT NOT NULL,
                             product_id BIGINT NOT NULL,

                             quantity INTEGER NOT NULL,
                             unit_price NUMERIC(19, 2) NOT NULL,
                             subtotal NUMERIC(19, 2) NOT NULL,

                             CONSTRAINT fk_order_items_order
                                 FOREIGN KEY (order_id)
                                     REFERENCES orders(id),

                             CONSTRAINT fk_order_items_product
                                 FOREIGN KEY (product_id)
                                     REFERENCES products(id)
);