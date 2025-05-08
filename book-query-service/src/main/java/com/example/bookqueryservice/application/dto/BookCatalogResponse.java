package com.example.bookqueryservice.application.dto;

import java.time.LocalDate;
import java.util.Set;

import com.example.bookqueryservice.domain.BookCatalog;

public record BookCatalogResponse(
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
    public static BookCatalogResponse from(BookCatalog bookCatalog){
        return new BookCatalogResponse(
            bookCatalog.getBookId(),
            bookCatalog.getOrganizationId(),
            bookCatalog.getTitle(),
            bookCatalog.getAuthor(),
            bookCatalog.getPublisher(),
            bookCatalog.getIsbn(),
            bookCatalog.getPublicationDate(),
            bookCatalog.getCategories(),
            bookCatalog.getBookStatus(),
            bookCatalog.getLocation()
        );
    }


    
}
