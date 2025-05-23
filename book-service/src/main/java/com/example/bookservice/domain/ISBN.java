package com.example.bookservice.domain;

import java.util.regex.Pattern;

import com.example.bookservice.exception.BookException;
import com.example.bookservice.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@EqualsAndHashCode
@Getter
public class ISBN {
    private static final Pattern ISBN13_PATTERN = Pattern.compile("^97[89]\\d{10}$");

    @Column(name = "isbn", nullable = false, unique = true, length = 13)
    private String isbn;

    protected ISBN() { }

    private ISBN(String isbn) {
        if (!isValid(isbn)) {
            throw new BookException(ErrorCode.BOOK_ISBN_INVALID);
        }
        this.isbn = isbn;
    }

    public static ISBN of(String isbn) {
        return new ISBN(isbn.replaceAll("-", ""));
    }

    private boolean isValid(String isbn) {
        if (!ISBN13_PATTERN.matcher(isbn).matches()) {
            return false;
        }
        return isValidChecksum(isbn);
    }

    private boolean isValidChecksum(String isbn) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = (10 - (sum % 10)) % 10;
        return checksum == Character.getNumericValue(isbn.charAt(12));
    }
    
}
