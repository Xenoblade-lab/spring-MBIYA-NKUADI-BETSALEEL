# spring-MBIYA-NKUADI-BETSALEEL

Application Spring Boot — **Restaurant Mbiya** (gestion de restaurant, Kinshasa).

**Auteur :** Mbiya Nkuadi Betsaleel

## Domaine métier

| Entité | Description |
|--------|-------------|
| **Catégorie** | Niveaux de fidélité (Bronze, Argent, Or, VIP, Nouveau) |
| **Plat** | Plats du menu avec prix en francs congolais |
| **Client** | Clients du restaurant avec quartier et points fidélité |
| **Commande** | Lien client ↔ plat avec note / avis |

## Prérequis

- Java 21
- MySQL 8 (Laragon)
- Maven (wrapper inclus)

## Base de données

```bash
mysql -u root --default-character-set=utf8mb4 < sql/schema.sql
mysql -u root --default-character-set=utf8mb4 < sql/reset-data.sql
```

Base : `restaurant_mbiya_db` — utilisateur `root` sans mot de passe (profil `dev`).

## Lancement

```bash
./mvnw spring-boot:run
```

Application : [http://localhost:8082](http://localhost:8082)

## URLs

| Module | Web | API REST |
|--------|-----|----------|
| Catégories | `/categories` | `/api/categories` |
| Clients | `/clients` | `/api/clients` |
| Plats | `/plats` | `/api/plats` |
| Commandes | `/commandes` | `/api/commandes` |

## Tests

```bash
./mvnw test -DforkCount=0
```

## Données démo

Client principal (ID 1) : **Mbiya Nkuadi Betsaleel** — Gombe, Kinshasa — catégorie VIP.
