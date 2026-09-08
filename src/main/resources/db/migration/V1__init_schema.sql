CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       nome VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE categorie (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nome VARCHAR(255) NOT NULL,
                           colore VARCHAR(255) NOT NULL,
                           user_id BIGINT NOT NULL,
                           CONSTRAINT fk_categoria_user FOREIGN KEY (user_id) REFERENCES users(id),
                           CONSTRAINT uk_categoria_nome_user UNIQUE (nome, user_id)
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