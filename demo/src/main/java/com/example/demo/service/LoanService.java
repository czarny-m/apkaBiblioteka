package com.example.demo.service;

import com.example.demo.exception.BookNotAvailableException;
import com.example.demo.exception.InvalidLoanRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.TooManyLoansException;
import com.example.demo.model.Book;
import com.example.demo.model.Loan;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public Loan addLoan(Loan loan) {
        // Na tym etapie API przyjmuje Loan. Walidujemy identyfikatory,
        // ale w nastepnym etapie zamienimy encje w @RequestBody na DTO.
        if (loan == null
                || loan.getUser() == null || loan.getUser().getId() == null
                || loan.getBook() == null || loan.getBook().getId() == null) {
            throw new InvalidLoanRequestException("Wymagane sa user.id oraz book.id.");
        }

        Long userId = loan.getUser().getId();
        Long bookId = loan.getBook().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nie znaleziono uzytkownika o ID: " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Nie znaleziono ksiazki o ID: " + bookId));

        List<Loan> activeLoans = loanRepository.findByUserIdAndReturnedFalse(userId);
        if (activeLoans.size() >= 3) {
            throw new TooManyLoansException(
                    "Uzytkownik moze wypozyczyc maksymalnie 3 ksiazki jednoczesnie.");
        }

        // Nigdy nie ufamy polu available przeslanemu w JSON.
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Ta ksiazka jest juz wypozyczona.");
        }

        // Stan wypozyczenia ustala serwer, nie klient.
        loan.setId(null);
        loan.setUser(user);
        loan.setBook(book);
        loan.setBorrowDate(null);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(null);
        loan.setReturned(false);

        book.setAvailable(false);
        bookRepository.save(book);
        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Transactional
    public void returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono wypozyczenia o ID: " + loanId));

        if (loan.getReturnDate() != null) {
            throw new RuntimeException("Ta ksiazka zostala juz wczesniej zwrocona.");
        }

        loan.setReturnDate(LocalDate.now());
        loan.setReturned(true);
        Book book = loan.getBook();
        book.setAvailable(true);

        loanRepository.save(loan);
        bookRepository.save(book);
    }

    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }
}
