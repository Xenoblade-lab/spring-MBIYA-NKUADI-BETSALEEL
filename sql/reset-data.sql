USE rdv_medical_mbiya_db;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE rendezvous;
TRUNCATE TABLE medecins;
TRUNCATE TABLE patients;
TRUNCATE TABLE specialites;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO specialites (libelle) VALUES
('Cardiologie'),
('Pediatrie'),
('Dermatologie'),
('Medecine generale'),
('Gynecologie');

INSERT INTO patients (nom, age) VALUES
('Mbiya Nkuadi Betsaleel', 28),
('Mukendi Grace', 35),
('Kabila Jean-Pierre', 42),
('Tshimanga Sarah', 19),
('Mbuyi Paul', 55);

INSERT INTO medecins (specialite_pk, nom_complet, etablissement, email, annees_experience) VALUES
(4, 'Dr. Lumumba Patrick', 'Clinique Ngaliema', 'p.lumumba@clinique.rdc', 12),
(1, 'Dr. Kasongo Marie', 'Hopital Monkole', 'm.kasongo@clinique.rdc', 8),
(2, 'Dr. Ilunga David', 'Centre Maman Yemo', 'd.ilunga@clinique.rdc', 15),
(3, 'Dr. Mutombo Aline', 'Polyclinique Gombe', 'a.mutombo@clinique.rdc', 6),
(5, 'Dr. Nzeba Christine', 'Clinique La Providence', 'c.nzeba@clinique.rdc', 10);

INSERT INTO rendezvous (medecin_pk, patient_pk, motif) VALUES
(1, 1, 'Consultation de controle et bilan de sante.'),
(1, 3, 'Douleurs thoraciques depuis trois jours.'),
(2, 4, 'Fievre et toux chez un adolescent.'),
(3, 2, 'Eruption cutanee au niveau des bras.'),
(4, 5, 'Suivi hypertension arterielle.'),
(2, 1, 'Visite pediatrique pour conseil nutritionnel.');
