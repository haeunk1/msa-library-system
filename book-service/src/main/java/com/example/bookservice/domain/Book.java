package com.example.bookservice.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.example.bookservice.application.port.in.Command.RegisterBookCommand;
import com.example.bookservice.exception.BookException;
import com.example.bookservice.exception.ErrorCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long organizationId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Embedded
    @AttributeOverride(name = "isbn", column = @Column(name = "isbn", unique = true))
    private ISBN isbn;

    @Column(nullable = false)
    private LocalDate publicationDate;

    @ElementCollection
    @CollectionTable(name = "book_category", joinColumns = @JoinColumn(name = "book_id"))
    private Set<BookCategory> categories = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus bookStatus;

    private String location;

    protected Book (){}

    public Book(Long organizationId, String title, String author, String publisher, ISBN isbn
    , LocalDate publicationDate, Set<BookCategory> categories, BookStatus bookStatus, String location){
        this.organizationId = organizationId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.categories = categories;
        this.bookStatus = bookStatus;
        this.location = location;
        assertValidCategories();
    }

    public static Book of(RegisterBookCommand command){
        return new Book(
            command.organizationId(),
            command.title(), 
            command.author(), 
            command.publisher(), 
            command.isbn(), 
            command.publicationDate(), 
            command.categories(), 
            command.bookStatus(), 
            command.location());
    }

    public void updateCategories(Set<BookCategory> categoryNames) {
        this.categories = categoryNames;
        assertValidCategories();
    }

    protected void assertValidCategories() {
        if (categories == null || categories.isEmpty()){
            throw new BookException(ErrorCode.BOOK_CATEGORY_REQUIRED);
        }
    }

    public void updateBookStatus(BookStatus bookStatus){
        this.bookStatus = bookStatus;
    }

    public void updateBookLocation(String location){
        this.location = location;
    }
}