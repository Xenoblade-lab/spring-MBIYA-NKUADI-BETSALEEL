CREATE DATABASE IF NOT EXISTS restaurant_mbiya_db
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE restaurant_mbiya_db;

CREATE TABLE IF NOT EXISTS categories (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(45) NOT NULL,
    UNIQUE KEY uk_categories_libelle (libelle)
);

CREATE TABLE IF NOT EXISTS plats (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom     VARCHAR(100) NOT NULL,
    prix_fc INT NOT NULL,
    UNIQUE KEY uk_plats_nom (nom)
);

CREATE TABLE IF NOT EXISTS clients (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    categorie_pk    BIGINT NOT NULL,
    nom_complet     VARCHAR(100) NOT NULL,
    quartier        VARCHAR(45) NOT NULL,
    email           VARCHAR(80) NOT NULL,
    points_fidelite INT NOT NULL,
    CONSTRAINT fk_clients_categorie
        FOREIGN KEY (categorie_pk) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS commandes (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_pk  BIGINT NOT NULL,
    plat_pk    BIGINT NOT NULL,
    note       VARCHAR(255) NOT NULL,
    CONSTRAINT fk_commandes_client
        FOREIGN KEY (client_pk) REFERENCES clients(id),
    CONSTRAINT fk_commandes_plat
        FOREIGN KEY (plat_pk) REFERENCES plats(id),
    UNIQUE KEY uk_client_plat (client_pk, plat_pk)
);

CREATE INDEX idx_clients_categorie ON clients(categorie_pk);
CREATE INDEX idx_commandes_client ON commandes(client_pk);
CREATE INDEX idx_commandes_plat ON commandes(plat_pk);
