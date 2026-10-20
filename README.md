# 📘 Library Management API

Edukacyjne REST API biblioteki oparte na Spring Boot, Spring Data JPA i PostgreSQL.

## Technologie
- Java 17 (ustawiona w `demo/pom.xml`), Spring Boot 3.4.3
- PostgreSQL, Spring Data JPA, Maven, Lombok
- JUnit 5 + Mockito

## Uruchomienie lokalne
1. Uruchom PostgreSQL i utwórz bazę `library_db`.
2. Skonfiguruj zmienną środowiskową `DB_PASSWORD` (hasło swojego lokalnego użytkownika PostgreSQL). Opcjonalnie ustaw `DB_USERNAME` i `DB_URL`.
3. Uruchom w katalogu `demo`:

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

Uwaga: istniejący test `DemoApplicationTests` uruchamia kontekst i wymaga dostępnej bazy danych. Testy jednostkowe `LoanServiceTest` używają Mockito. Nie przechowuj haseł w repozytorium.

## Endpointy
- `GET /api/books`, `GET /api/books/{id}`, `GET /api/books/available`, `POST /api/books`, `DELETE /api/books/{id}`
- `GET /api/users`, `GET /api/users/{id}`, `POST /api/users`, `DELETE /api/users/{id}`
- `GET /api/loans`, `GET /api/loans/user/{userId}`, `POST /api/loans`, `PUT /api/loans/return/{loanId}`
- `GET /api/reservations`, `POST /api/reservations`, `DELETE /api/reservations/{id}`

## Przykładowe żądania
```http
POST /api/books
Content-Type: application/json

{"title":"Wiedźmin: Ostatnie życzenie","author":"Andrzej Sapkowski","isbn":"9788375780635","available":true}
```

```http
POST /api/users
Content-Type: application/json

{"name":"Jan Kowalski","email":"jan@example.com"}
```

```http
POST /api/loans
Content-Type: application/json

{"user":{"id":1},"book":{"id":1}}
```

## Etap nauki 1 — poprawione wypożyczenia
- `LoanService.addLoan` ma `@Transactional`: zapis książki i wypożyczenia jest atomowy w jednej transakcji.
- Pobieramy książkę i użytkownika z repozytoriów po ID: pola wysłane w JSON (np. `available`) nie decydują o rzeczywistym stanie.
- Brak identyfikatorów daje HTTP 400, brak użytkownika lub książki — HTTP 404.
- Brak dostępności i limit 3 aktywnych wypożyczeń blokują operację.
- Serwer ustawia datę i status wypożyczenia; testy jednostkowe pokrywają podstawowe scenariusze.

### Ograniczenia obecnego etapu
- Brakuje zabezpieczenia współbieżności: dwie jednoczesne transakcje mogą odczytać ten sam stan. Następny etap: blokowanie lub wersjonowanie encji oraz test integracyjny na PostgreSQL.
- API nadal przyjmuje encje JPA. Następny etap: żądania DTO z walidacją i odpowiedzi DTO.
- Nie ma uwierzytelniania, autoryzacji, migracji Flyway ani pełnych testów integracyjnych.
- `ddl-auto=update` jest ustawieniem developerskim; nie należy polegać na nim przy wdrożeniu.
- `Book` reprezentuje pojedynczą dostępną pozycję; przyszłe rozszerzenie wydzieli egzemplarze `BookCopy`.

## Weryfikacja (w katalogu demo)
```powershell
.\mvnw.cmd -Dtest=LoanServiceTest test
.\mvnw.cmd test
```

Po uruchomieniu aplikacji spróbuj wypożyczyć ten sam egzemplarz dwa razy kolejno. Drugie żądanie powinno dać HTTP 400. W tej wersji nie testujemy jeszcze równoczesnych żądań.

## Autor
Projekt edukacyjny: Mikołaj Niebrzegowski — https://github.com/czarny-m
