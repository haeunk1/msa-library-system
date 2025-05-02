package com.example.bookservice.domain;

import java.util.regex.Pattern;

import com.example.bookservice.domain.exception.InvalidIsbnException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ISBN {
    private static final Pattern ISBN13_PATTERN = Pattern.compile("^97[89]\\d{10}$");

    @Column(name = "isbn", nullable = false, unique = true, length = 13)
    private String value;

    protected ISBN() { }

    private ISBN(String value) {
        if (!isValid(value)) {
            throw new InvalidIsbnException(value);
        }
        this.value = value;
    }

    public static ISBN of(String value) {
        return new ISBN(value.replaceAll("-", ""));
    }

    public String getValue() {
        return value;
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
