package com.example.bookservice.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


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

    public Book(String title, String author, String publisher, String isbn
    , LocalDate publicationDate, Set<BookCategory> categories, BookStatus bookStatus, String location){
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = ISBN.of(isbn);
        this.publicationDate = publicationDate;
        this.categories = categories;
        this.bookStatus = bookStatus;
        this.location = location;
        assertValidCategories();
    }

    public static Book of(String title, String author, String publisher, String isbn
    , LocalDate publicationDate, Set<BookCategory> categories, BookStatus bookStatus, String location){
        return new Book(title, author, publisher, isbn, publicationDate, categories, bookStatus, location);
    }

    public void updateCategories(Set<String> categoryNames) {
        this.categories = BookCategory.of(categoryNames);
        assertValidCategories();
    }

    protected void assertValidCategories() {
        if (categories == null || categories.isEmpty()){
            throw new IllegalArgumentException("하나 이상의 카테고리가 입력되어야 합니다.");
        }
    }

    public void changeBookStatus(BookStatus bookStatus){
        this.bookStatus = bookStatus;
    }
}