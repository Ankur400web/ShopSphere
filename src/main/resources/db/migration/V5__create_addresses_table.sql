CREATE TABLE addresses (
                           id BIGSERIAL PRIMARY KEY,

                           user_id BIGINT NOT NULL,

                           full_name VARCHAR(255) NOT NULL,
                           street VARCHAR(255) NOT NULL,
                           city VARCHAR(100) NOT NULL,
                           state VARCHAR(100) NOT NULL,
                           postal_code VARCHAR(20) NOT NULL,
                           country VARCHAR(100) NOT NULL,
                           phone_number VARCHAR(30) NOT NULL,

                           is_default BOOLEAN NOT NULL DEFAULT FALSE,

                           CONSTRAINT fk_addresses_user
                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE
);