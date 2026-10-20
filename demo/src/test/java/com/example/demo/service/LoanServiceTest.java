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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock private LoanRepository loanRepository;
    @Mock private BookRepository bookRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private LoanService loanService;

    private User user;
    private Book book;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        book = new Book();
        book.setId(5L);
        book.setAvailable(true);
    }

    private Loan request() {
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        return loan;
    }

    @Test
    void createsLoanAndMarksBookUnavailable() {
        Loan loan = request();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(5L)).thenReturn(Optional.of(book));
        when(loanRepository.findByUserIdAndReturnedFalse(1L)).thenReturn(List.of());
        when(loanRepository.save(any(Loan.class))).thenAnswer(i -> i.getArgument(0));

        Loan result = loanService.addLoan(loan);

        assertFalse(book.isAvailable());
        assertSame(book, result.getBook());
        assertSame(user, result.getUser());
        assertNotNull(result.getLoanDate());
        assertFalse(result.isReturned());
        verify(bookRepository).save(book);
        verify(loanRepository).save(loan);
    }

    @Test
    void rejectsBookMarkedUnavailableInDatabaseEvenWhenRequestSaysAvailable() {
        Book persistedBook = new Book();
        persistedBook.setId(5L);
        persistedBook.setAvailable(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(5L)).thenReturn(Optional.of(persistedBook));
        when(loanRepository.findByUserIdAndReturnedFalse(1L)).thenReturn(List.of());

        assertThrows(BookNotAvailableException.class, () -> loanService.addLoan(request()));
        verify(loanRepository, never()).save(any());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void rejectsRequestWithoutBookId() {
        Loan loan = request();
        loan.getBook().setId(null);
        assertThrows(InvalidLoanRequestException.class, () -> loanService.addLoan(loan));
        verifyNoInteractions(bookRepository, userRepository, loanRepository);
    }

    @Test
    void rejectsUnknownUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> loanService.addLoan(request()));
        verify(loanRepository, never()).save(any());
    }

    @Test
    void rejectsUnknownBook() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> loanService.addLoan(request()));
        verify(loanRepository, never()).save(any());
    }

    @Test
    void rejectsMoreThanThreeActiveLoans() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(5L)).thenReturn(Optional.of(book));
        when(loanRepository.findByUserIdAndReturnedFalse(1L))
                .thenReturn(List.of(new Loan(), new Loan(), new Loan()));

        assertThrows(TooManyLoansException.class, () -> loanService.addLoan(request()));
        verify(loanRepository, never()).save(any());
        verify(bookRepository, never()).save(any());
    }
}
