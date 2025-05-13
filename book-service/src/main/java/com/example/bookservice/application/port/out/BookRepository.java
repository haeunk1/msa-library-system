package com.example.bookservice.application.port.out;

import java.util.Optional;

import com.example.bookservice.domain.Book;
import com.example.bookservice.domain.ISBN;

public interface BookRepository{
    Book save(Book book);
    Optional<Book> findByIsbn(ISBN isbn);
    Optional<Book> findById(Long id);
}
