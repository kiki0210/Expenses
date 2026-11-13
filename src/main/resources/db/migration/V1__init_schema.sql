CREATE TABLE houseHold (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       nome VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       household_id BIGINT,
                       is_household_admin BOOLEAN NOT NULL DEFAULT FALSE,
                       CONSTRAINT fk_user_household FOREIGN KEY (household_id) REFERENCES houseHold(id)
);

CREATE TABLE categorie (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nome VARCHAR(255) NOT NULL,
                           colore VARCHAR(255) NOT NULL,
                           household_id BIGINT NOT NULL,
                           CONSTRAINT fk_categoria_houseHold FOREIGN KEY (household_id ) REFERENCES houseHold(id),
                           CONSTRAINT uk_categoria_nome_houseHold UNIQUE (nome, household_id)
);

CREATE TABLE spese (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       descrizione VARCHAR(255),
                       importo DECIMAL(10,2) NOT NULL,
                       data DATE NOT NULL,
                       categoria_id BIGINT NOT NULL,
                       user_id BIGINT NOT NULL,
                       CONSTRAINT fk_spesa_categoria FOREIGN KEY (categoria_id) REFERENCES categorie(id),
                       CONSTRAINT fk_spesa_user FOREIGN KEY (user_id) REFERENCES users(id)
);