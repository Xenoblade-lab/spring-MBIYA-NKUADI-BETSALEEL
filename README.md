# spring-MBIYA-NKUADI-BETSALEEL

Application Spring Boot — **Gestion de rendez-vous medicaux** (Clinique Mbiya, Kinshasa).

**Auteur :** Mbiya Nkuadi Betsaleel

## Domaine metier

| Entite | Description |
|--------|-------------|
| **Specialite** | Cardiologie, pediatrie, dermatologie, medecine generale, gynecologie |
| **Patient** | Patients avec nom et age |
| **Medecin** | Praticiens lies a une specialite et un etablissement |
| **RendezVous** | Consultation planifiee entre un medecin et un patient |

## Prerequis

- Java 21
- MySQL 8 (Laragon)
- Maven (wrapper inclus)

## Base de donnees

```bash
mysql -u root --default-character-set=utf8mb4 < sql/schema.sql
mysql -u root --default-character-set=utf8mb4 < sql/reset-data.sql
```

Base : `rdv_medical_mbiya_db` — utilisateur `root` sans mot de passe (profil `dev`).

## Lancement

```bash
./mvnw spring-boot:run
```

Application : [http://localhost:8082](http://localhost:8082)

## URLs

| Module | Web | API REST |
|--------|-----|----------|
| Specialites | `/specialites` | `/api/specialites` |
| Medecins | `/medecins` | `/api/medecins` |
| Patients | `/patients` | `/api/patients` |
| Rendez-vous | `/rendezvous` | `/api/rendezvous` |

## Tests

```bash
./mvnw test -DforkCount=0
```

## Donnees demo

Patient principal (ID 1) : **Mbiya Nkuadi Betsaleel** — 28 ans — consultations avec Dr. Lumumba Patrick.
