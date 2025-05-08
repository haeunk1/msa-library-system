package com.example.bookqueryservice.domain;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.bookqueryservice.application.message.BookCreatedMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Document(collection = "book_catalog")
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class BookCatalog {
    @Id
    private Long bookId;
    private Long organizationId;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private LocalDate publicationDate;
    private Set<String> categories;
    private String bookStatus;
    private String location;
    private Long rentCnt;

    public static BookCatalog from(BookCreatedMessage message) {
        return new BookCatalog(message.bookId(),message.organizationId(),
        message.title(),message.author(),message.publisher(),message.isbn()
        ,message.publicationDate(),message.categories()
        ,message.bookStatus(),message.location(),1L
        );
    }
}
