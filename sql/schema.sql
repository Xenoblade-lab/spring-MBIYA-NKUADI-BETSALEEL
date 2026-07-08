CREATE DATABASE IF NOT EXISTS rdv_medical_mbiya_db
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE rdv_medical_mbiya_db;

CREATE TABLE IF NOT EXISTS specialites (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(45) NOT NULL,
    UNIQUE KEY uk_specialites_libelle (libelle)
);

CREATE TABLE IF NOT EXISTS patients (
    id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    UNIQUE KEY uk_patients_nom (nom)
);

CREATE TABLE IF NOT EXISTS medecins (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    specialite_pk      BIGINT NOT NULL,
    nom_complet        VARCHAR(100) NOT NULL,
    etablissement      VARCHAR(45) NOT NULL,
    email              VARCHAR(80) NOT NULL,
    annees_experience  INT NOT NULL,
    CONSTRAINT fk_medecins_specialite
        FOREIGN KEY (specialite_pk) REFERENCES specialites(id)
);

CREATE TABLE IF NOT EXISTS rendezvous (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    medecin_pk BIGINT NOT NULL,
    patient_pk BIGINT NOT NULL,
    motif      VARCHAR(255) NOT NULL,
    CONSTRAINT fk_rendezvous_medecin
        FOREIGN KEY (medecin_pk) REFERENCES medecins(id),
    CONSTRAINT fk_rendezvous_patient
        FOREIGN KEY (patient_pk) REFERENCES patients(id),
    UNIQUE KEY uk_medecin_patient (medecin_pk, patient_pk)
);

CREATE INDEX idx_medecins_specialite ON medecins(specialite_pk);
CREATE INDEX idx_rendezvous_medecin ON rendezvous(medecin_pk);
CREATE INDEX idx_rendezvous_patient ON rendezvous(patient_pk);
