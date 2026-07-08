-- Réinitialise la base avec le jeu de données de démonstration.
-- Exécuter dans HeidiSQL ou : mysql -u root --default-character-set=utf8mb4 < sql/reset-data.sql

USE restaurant_mbiya_db;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE commandes;
TRUNCATE TABLE clients;
TRUNCATE TABLE plats;
TRUNCATE TABLE categories;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO categories (libelle) VALUES
('Bronze'),
('Argent'),
('Or'),
('VIP'),
('Nouveau');

INSERT INTO plats (nom, prix_fc) VALUES
('Poulet braisé et frites', 15000),
('Liboke de tilapia', 18000),
('Fumbwa avec poisson fumé', 12000),
('Madesu na nyama', 10000),
('Chikwangue et saka-saka', 8000);

INSERT INTO clients (categorie_pk, nom_complet, quartier, email, points_fidelite) VALUES
(4, 'Mbiya Nkuadi Betsaleel', 'Gombe', 'mbiya.betsaleel@restaurant.rdc', 320),
(3, 'Mukendi Grace', 'Limete', 'grace.mukendi@restaurant.rdc', 180),
(2, 'Kabila Jean-Pierre', 'Bandal', 'jp.kabila@restaurant.rdc', 95),
(1, 'Tshimanga Sarah', 'Kintambo', 'sarah.tshimanga@restaurant.rdc', 45),
(5, 'Mbuyi Paul', 'Ngaliema', 'paul.mbuyi@restaurant.rdc', 12);

INSERT INTO commandes (client_pk, plat_pk, note) VALUES
(1, 1, 'Poulet bien croustillant, service rapide.'),
(1, 3, 'Portion généreuse, parfait pour le déjeuner.'),
(2, 2, 'Liboke savoureux, épices bien dosées.'),
(3, 1, 'Commande à emporter, bien emballée.'),
(4, 5, 'Chikwangue fondant, accompagnement excellent.'),
(5, 4, 'Première visite, très satisfait du plat.');
