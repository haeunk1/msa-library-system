package com.example.bookqueryservice.application.message;

import java.time.LocalDate;
import java.util.Set;

public record BookCreatedMessage(
    Long bookId,
    Long organizationId,
    String title,
    String author,
    String publisher,
    String isbn,
    LocalDate publicationDate,
    Set<String> categories,
    String bookStatus,
    String location
    ) {}