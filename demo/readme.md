# 📘 Library Management API

Prosty projekt REST API do zarządzania książkami i wypożyczeniami w bibliotece. Zrealizowany w Spring Boot + PostgreSQL.

---

## 🚀 Technologie

- Java 21
- Spring Boot 3.4
- Spring Data JPA
- PostgreSQL 17
- Maven
- Lombok
- Postman (testowanie)

---

## 🏗️ Funkcjonalności

### 📚 Książki
- [x] Dodawanie książek
- [x] Pobieranie listy książek
- [x] Sprawdzanie dostępności książki

### 👤 Użytkownicy
- [x] Dodawanie użytkownika
- [x] Pobieranie listy użytkowników

### 🔄 Wypożyczenia
- [x] Wypożyczenie książki przez użytkownika
- [x] Zwracanie książki
- [x] Walidacja: max. 3 aktywne wypożyczenia na użytkownika
- [x] Obsługa wyjątków

---

## 🔌 Endpointy REST

### 📖 Książki
| Metoda | Endpoint         | Opis                   |
|--------|------------------|------------------------|
| GET    | `/api/books`     | Lista wszystkich książek |
| POST   | `/api/books`     | Dodaj nową książkę     |

### 👤 Użytkownicy
| Metoda | Endpoint         | Opis                   |
|--------|------------------|------------------------|
| GET    | `/api/users`     | Lista użytkowników     |
| POST   | `/api/users`     | Dodaj nowego użytkownika |

### 🔄 Wypożyczenia
| Metoda | Endpoint                 | Opis                        |
|--------|--------------------------|-----------------------------|
| GET    | `/api/loans`             | Lista wypożyczeń            |
| POST   | `/api/loans`             | Dodaj wypożyczenie          |
| PUT    | `/api/loans/return/{id}` | Zwróć książkę               |

---

## 🧪 Przykładowe zapytania (Postman)

### Dodanie książki:
```json
POST /api/books
{
  "title": "Wiedźmin: Ostatnie życzenie",
  "author": "Andrzej Sapkowski",
  "isbn": "9788375780635",
  "available": true
}
```

### Dodanie użytkownika:
```json
POST /api/users
{
  "name": "Jan Kowalski",
  "email": "jan@example.com"
}
```

### Wypożyczenie:
```json
POST /api/loans
{
  "user": { "id": 1 },
  "book": { "id": 1 }
}
```

---

## 🧠 Autor projektu

> Projekt zrealizowany edukacyjnie przez **Mikołaj Niebrzegowski**  
> Mentor: ChatGPT - AI backend dev z 15-letnim doświadczeniem 😎