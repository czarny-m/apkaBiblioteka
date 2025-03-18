package com.example.demo.exception;



public class TooManyLoansException extends RuntimeException {
    public TooManyLoansException(String message) {
        super(message);
    }
}
