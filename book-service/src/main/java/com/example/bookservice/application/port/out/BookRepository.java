package com.example.bookservice.application.port.out;

import java.util.Optional;

import com.example.bookservice.domain.Book;

public interface BookRepository{
    Book save(Book book);
    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findById(Long id);
}
