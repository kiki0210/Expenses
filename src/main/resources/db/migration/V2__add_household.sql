CREATE TABLE houseHold (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

ALTER TABLE users ADD COLUMN household_id BIGINT;
ALTER TABLE users ADD COLUMN is_household_admin BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE users ADD CONSTRAINT fk_user_household FOREIGN KEY (household_id) REFERENCES houseHold(id);

-- categorie passa da vincolo per-utente a vincolo per-gruppo
ALTER TABLE categorie ADD COLUMN household_id BIGINT;
ALTER TABLE categorie DROP CONSTRAINT uk_categoria_nome_user;
-- gruppo_id resta nullable finché non migri i dati esistenti; poi eventualmente NOT NULL in una V3 separata