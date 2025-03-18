# 📘 Library Management API
Prosty projekt REST API do zarządzania książkami i wypożyczeniami w bibliotece. Zrealizowany w Spring Boot + PostgreSQL.

# 🚀 Technologie
- **Java 21**
- **Spring Boot 3.4**
- **Spring Data JPA**
- **PostgreSQL 17**
- **Maven**
- **Lombok**
- **Postman (testowanie)**

# 🏗️ Funkcjonalności
# 📚 Książki
 - **Dodawanie książek**
 - **Pobieranie listy książek**
 - **Sprawdzanie dostępności książki**
# 👤 Użytkownicy
 -**Dodawanie użytkownika**
 -**Pobieranie listy użytkowników**
# 🔄 Wypożyczenia
 - Wypożyczenie książki przez użytkownika
 - Zwracanie książki
 - Walidacja: max. 3 aktywne wypożyczenia na użytkownika
 - Obsługa wyjątków**
# 🔌 Endpointy REST
# 📖 Książki
- **Metoda	Endpoint	Opis**
- GET	/api/books	Lista wszystkich książek
- POST	/api/books	Dodaj nową książkę
# 👤 Użytkownicy
-**Metoda	Endpoint	Opis**
- GET	/api/users	Lista użytkowników
-  POST	/api/users	Dodaj nowego użytkownika
# 🔄 Wypożyczenia
-**Metoda	Endpoint	Opis**
- GET	/api/loans	Lista wypożyczeń
- POST	/api/loans	Dodaj wypożyczenie
- PUT	/api/loans/return/{id}	Zwróć książkę
# 🧪 Przykładowe zapytania (Postman)
- **Dodanie książki:**
POST /api/books
{
  "title": "Wiedźmin: Ostatnie życzenie",
  "author": "Andrzej Sapkowski",
  "isbn": "9788375780635",
  "available": true
}
- **Dodanie użytkownika:**
POST /api/users
{
  "name": "Jan Kowalski",
  "email": "jan@example.com"
}
- **Wypożyczenie:**
POST /api/loans
{
  "user": { "id": 1 },
  "book": { "id": 1 }
}
# 🧠 Autor projektu
** Projekt zrealizowany edukacyjnie przez Mikołaj Niebrzegowski ** 
** Mentor: ChatGPT - AI backend dev z 15-letnim doświadczeniem 😎 **

