package com.example.bookservice.domain.event;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.bookservice.domain.Book;
import com.example.bookservice.domain.BookCategory;

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
) {

    public static BookCreatedMessage from(Book book) {
        Set<String> categories = book.getCategories().stream().map(BookCategory::getName).collect(Collectors.toSet());
        return new BookCreatedMessage(
            book.getId(),
            book.getOrganizationId(),
            book.getTitle(),
            book.getAuthor(),
            book.getPublisher(),
            book.getIsbn().getIsbn(),
            book.getPublicationDate(),
            categories,
            book.getBookStatus().toString(),
            book.getLocation()
        );
    }
}