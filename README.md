# 📘 Library Management API
Prosty projekt REST API do zarządzania książkami i wypożyczeniami w bibliotece. Zrealizowany w Spring Boot + PostgreSQL.

#🚀 Technologie
- **Java 21
- **Spring Boot 3.4
- **Spring Data JPA
- **PostgreSQL 17
- **Maven
- **Lombok
- **Postman (testowanie)

# 🏗️ Funkcjonalności
# 📚 Książki
 -**Dodawanie książek
 -**Pobieranie listy książek
 -**Sprawdzanie dostępności książki
# 👤 Użytkownicy
 -**Dodawanie użytkownika
 -**Pobieranie listy użytkowników
#🔄 Wypożyczenia
 -**Wypożyczenie książki przez użytkownika
 -**Zwracanie książki
 -**Walidacja: max. 3 aktywne wypożyczenia na użytkownika
 -**Obsługa wyjątków
#🔌 Endpointy REST
#📖 Książki
-**Metoda	Endpoint	Opis
-**GET	/api/books	Lista wszystkich książek
-**POST	/api/books	Dodaj nową książkę
#👤 Użytkownicy
-**Metoda	Endpoint	Opis
-**GET	/api/users	Lista użytkowników
-**POST	/api/users	Dodaj nowego użytkownika
# 🔄 Wypożyczenia
-**Metoda	Endpoint	Opis
-**GET	/api/loans	Lista wypożyczeń
-**POST	/api/loans	Dodaj wypożyczenie
-**PUT	/api/loans/return/{id}	Zwróć książkę
# 🧪 Przykładowe zapytania (Postman)
-**Dodanie książki:
POST /api/books
{
  "title": "Wiedźmin: Ostatnie życzenie",
  "author": "Andrzej Sapkowski",
  "isbn": "9788375780635",
  "available": true
}
-**Dodanie użytkownika:
POST /api/users
{
  "name": "Jan Kowalski",
  "email": "jan@example.com"
}
-**Wypożyczenie:
POST /api/loans
{
  "user": { "id": 1 },
  "book": { "id": 1 }
}
#🧠 Autor projektu
** Projekt zrealizowany edukacyjnie przez Mikołaj Niebrzegowski
** Mentor: ChatGPT - AI backend dev z 15-letnim doświadczeniem 😎

# apkaInzynierka

# 📘 Library Management API

## 📌 Opis projektu
"Mój Dom" to aplikacja mobilna do zarządzania inteligentnym domem. Pozwala użytkownikom dodawać i zarządzać pomieszczeniami, urządzeniami oraz scenariuszami automatyzacji. Główne funkcjonalności obejmują:

- **Dodawanie i edycję pokoi**
- **Zarządzanie urządzeniami** (dodawanie, edycja, przypisywanie do pokoi)
- **Tworzenie scenariuszy** automatyzacji
- **Połączenie urządzeń** z systemem
- **Sekcja ustawień i FAQ** dla użytkownika

## 🔧 Technologie
Projekt wykorzystuje nowoczesne rozwiązania:

- **Kotlin** – główny język programowania
- **Jetpack Compose** – deklaratywne UI
- **Cloud Firestore** – baza danych
- **Navigation Compose** – nawigacja między ekranami
- **Material 3** – nowoczesny design

## 📂 Struktura projektu

├── app/src/main/java/com/example/a2dni

│   ├── MainActivity.kt  # Główna aktywność

│   ├── Screens.kt  # Definicja ekranów aplikacji

│   ├── Ekrany/

│   │   ├── DodajPokoj.kt  # Ekran dodawania pokoju

│   │   ├── DodajScenariusz.kt  # Tworzenie scenariuszy

│   │   ├── DodajUrządzenie.kt  # Dodawanie urządzeń

│   │   ├── Edytuj.kt  # Edycja elementów

│   └── viewmodel/  # Warstwa logiki biznesowej


## 🏆 Potencjalne rozwinięcie projektu
- Obsługa większej liczby urządzeń 
- Integracja z asystentami głosowymi 
- Lepsza personalizacja scenariuszy 
- Usprawnienie interfejsu użytkownika

## 📩 Kontakt
Jeśli masz pytania, skontaktuj się ze mną:
- 📧 Email: TwójEmail@example.com
- 💼 LinkedIn: https://www.linkedin.com/in/miko%C5%82aj-niebrzegowski-52b81b351/
- 🔗 GitHub: https://github.com/czarny-m

