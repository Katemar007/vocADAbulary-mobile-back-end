# 🛠 vocADAbulary — Backend API (Spring Boot)

Java Spring Boot REST API that powers **Tech voice**: flashcards, wallet, quizzes, sentence constructor (learned-words-only), and progress summary. PostgreSQL for storage, Flyway for schema migrations.

---

## 0. Tech Stack

- **Language/Runtime:** Java 17+
- **Framework:** Spring Boot 3.x (Spring Web, Spring Data JPA, Validation)
- **Persistence:** Hibernate (JPA)
- **Database:** PostgreSQL 14+
- **Migrations:** Flyway
- **JSON:** Jackson
- **HTTP Client:** RestTemplate
- **AI Integration:** OpenAI API (TTS, phonetics, sentence generation)
- **Build:** Maven + Maven Wrapper
- **Hosting (prod):** Render (environment variables)

## 1. Architecture at a Glance

**Spring components used**

- **Authorisation** (`com.vocadabulary.auth`) — Mock User integration, requests interception, context and configuration setting
- **Controllers** (`..controllers`) — HTTP endpoints (`/api/**`). Thin; delegate to services.
- **Services** (`..services`) — business logic, validation, orchestration.
- **Repositories** (`..repositories`) — Spring Data JPA interfaces for DB access.
- **Models** (`..models`) — JPA @Entity classes.
- **DTOs** (`..dto`) — request/response payloads, decouple API from entities.
- **Requests** (`.requests` artifacts related to client-side interactions with the Spring Boot backend

**Typical request flow**
`Client → Controller → Service → Repository → DB` → back through DTO mapping → response


## 2. Project Structure (General Tree)

```
src/
├─ main/
│  ├─ java/com/vocadabulary/
│  │  ├─ auth/
│  │  ├─ controllers/
│  │  ├─ dto/
│  │  ├─ controllers/
│  │  ├─ models/
│  │  ├─ repositories/
│  │  ├─ requests/
│  │  ├─ services/
│  └─ resources/
│     ├─ application.properties           # base (no secrets)
│     ├─ application-local.properties     # local profile
│     ├─ application-prod.properties      # prod profile (env vars only)
│     └─ db/migration/                    # Flyway migrations (V1\_\_*.sql, V2\_\_*.sql, ...)
```


## 3. Configuration & Profiles

`application.properties': Default/base configuration for all environments
`application-local.properties': Local development configuration.
`application-prod.properties': Production configuration (not on Git - in .gitignore)

Select the profile at runtime with `--spring.profiles.active=local` or 

  ⚠️ **OpenAI API Key Required**

This app uses OpenAI's API to allow full access to all of the app features.
You'll need to get your own key.

**Setup**: Get your free key at [platform.openai.com/api-keys](https://platform.openai.com/api-keys) and add it to the local profile `application-local.properties`:

  `openai.api.key=${YOUR_OPENAI_API_KEY}`

Select local profile at runtime with `-Dspring-boot.run.profiles=loc`

## 4. Local Installation guide
Prerequisites:
- Java 17+
- Maven (wrapper included)
- PostgreSQL 14+ installed locally

Step 1: Clone the Repository and navigate into the project directory
```bash 
git clone https://github.com/Katemar007/vocADAbulary-mobile-back-end
cd your-spring-boot-repo
```
Step 2: Install dependencies
```bash

mvn clean install
```

Step 3: Export env vars (local)
```bash

export DB_URL=jdbc:postgresql://localhost:5432/vocadabulary
export DB_USER=postgres
export DB_PASSWORD=postgres
export OPENAI_API_KEY=dummy-local-key
```

Step 4: Create database

```bash:

  psql -U postgres -c "CREATE DATABASE vocadabulary_db;"
```
Step 5: Run the app with the local profile

```bash

mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## 5. API Base URLs
Local: http://localhost:8085/

Prod (Render): [https://vocadabulary-mobile-back-end.onrender.com](https://vocadabulary-mobile-back-end.onrender.com)

On a physical device (Expo), use your computer’s LAN IP instead of localhost.

## 6. Database Migrations (Flyway)
SQL files live in src/main/resources/db/migration.
Naming: V{version}__{description}.sql (e.g., V1__init_schema.sql).
Flyway runs automatically at startup. Avoid ddl-auto=update in production.

## 7. CORS (for mobile/frontend)
@CrossOrigin on controllers so Expo/devices can reach the API.

## 8. Troubleshooting

- **Cannot connect to DB**: verify Postgres is running and `DB_URL` is correct.
- **`relation ... does not exist`**: check Flyway logs; ensure migrations executed.
- **Port 8080 in use**: set `PORT=8081` or kill the other process.
- **CORS errors from device**: use LAN IP in frontend base URL and configure allowed origins.

