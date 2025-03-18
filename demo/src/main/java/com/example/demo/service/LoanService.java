package com.example.demo.service;


import com.example.demo.exception.BookNotAvailableException;
import com.example.demo.exception.TooManyLoansException;
import com.example.demo.model.Book;
import com.example.demo.model.Loan;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.TooManyListenersException;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public Loan addLoan(Loan loan) {

        // 1. Sprawdź aktywne wypożyczenia użytkownika
        List<Loan> activeLoans = loanRepository.findByUserIdAndReturnedFalse(loan.getUser().getId());

        if (activeLoans.size() >= 3) {
            throw new TooManyLoansException("Użytkownik może wypożyczyć maksymalnie 3 książki jednocześnie.");
        }

        // 🔒 2. Sprawdź dostępność książki
        Book book = loan.getBook();
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Ta książka jest już wypożyczona.");
        }

        // 2. Zapisz wypożyczenie
        loan.setReturned(false);
        loan.setLoanDate(LocalDate.now());

        // 3. Ustaw książkę jako niedostępną

        book.setAvailable(false);


        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Transactional
    public void returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono wypożyczenia o ID: " + loanId));

        if (loan.getReturnDate() != null) {
            throw new RuntimeException("Ta książka została już wcześniej zwrócona.");
        }

        loan.setReturnDate(LocalDate.now());
        loan.setReturned(true); // dodajemy oznaczenie, że zwrócona
        Book book = loan.getBook();
        book.setAvailable(true);

        // JPA zapisze zmiany dzięki @Transactional, ale jawne save() nie zaszkodzi:
        loanRepository.save(loan);
        bookRepository.save(book);
    }

    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

}


