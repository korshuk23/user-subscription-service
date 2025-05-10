# Subscription Service

## 📌 Описание

Микросервис на Spring Boot 3 для управления пользователями и их подписками на цифровые сервисы (YouTube Premium, VK Музыка, Netflix и др.).

- Язык: **Java 17**
- БД: **PostgreSQL**
- Документация: **Swagger**
- Сборка и запуск: **Docker / Docker Compose**
- Миграции: **Liquibase**

---

##  Функциональные возможности

### Пользователи (`/users`)
- `POST /users` — создать пользователя
- `GET /users/{id}` — получить информацию о пользователе
- `PUT /users/{id}` — обновить данные пользователя
- `DELETE /users/{id}` — удалить пользователя

### Подписки (`/users/{id}/subscriptions`)
- `POST` — добавить подписку
- `GET` — получить все подписки пользователя
- `DELETE /{sub_id}` — удалить конкретную подписку

### Топ подписок
- `GET /subscriptions/top` — получить ТОП-3 самых популярных подписок

---

##  Архитектура

- `User` — сущность пользователя
- `Subscription` — сущность подписки
- `UserService` / `SubscriptionService` — бизнес-логика
- `UserController` / `SubscriptionController`/ `SubscriptionAnalyticsController` — REST-контроллеры
- `UserRepository` / `SubscriptionRepository` — JPA-репозитории
- `SubscriptionMapper` / `UserMapper`  — маппинг DTO ↔ Entity
- `GlobalExceptionHandler` — проект использует глобальный обработчик ошибок для обработки исключений и возврата корректных HTTP-ответов с нужными статусами.

---

##  Технологии

- Java 17
- Spring Boot 3
- Spring Web, Spring Data JPA
- PostgreSQL
- Liquibase (миграции)
- SLF4J (логирование)
- MapStruct (маппинг DTO)
- Swagger / SpringDoc OpenAPI
- Docker / Docker Compose
- Gradle
- JUnit 5, Mockito (тестирование)

---

##  Тестирование

- `SubscriptionServiceTest` — покрытие всех методов сервиса, включая позитивные и негативные сценарии:
- `UserServiceTest` — покрытие всех методов сервиса, включая позитивные и негативные сценарии:

---

##  Запуск проекта

### Предварительные требования

- Java 17
- Docker + Docker Compose

### Локальный запуск

```bash
# Собрать проект
./gradlew clean build

# Запустить с помощью Docker Compose
docker-compose up --build