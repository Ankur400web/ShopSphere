CREATE TABLE revoked_at (
                              id BIGSERIAL PRIMARY KEY ,
                              token_hash VARCHAR(300) UNIQUE ,
                              user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE RESTRICT ,
                              expires_at TIMESTAMPTZ NOT NULL ,
                              revoke_at TIMESTAMPTZ ,
                              created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              last_used_at TIMESTAMPTZ
);