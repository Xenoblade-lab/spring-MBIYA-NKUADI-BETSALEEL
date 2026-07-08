USE rdv_medical_mbiya_db;
ALTER DATABASE rdv_medical_mbiya_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE specialites CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE patients CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE medecins CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE rendezvous CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
