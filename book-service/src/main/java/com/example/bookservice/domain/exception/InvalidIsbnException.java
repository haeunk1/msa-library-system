package com.example.bookservice.domain.exception;

public class InvalidIsbnException extends RuntimeException {
    public InvalidIsbnException(String isbn) {
        super("유효하지 않은 ISBN입니다: " + isbn);
    }
}
