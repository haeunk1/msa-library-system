package com.example.bookservice.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.bookservice.application.port.out.BookRepository;
import com.example.bookservice.domain.Book;
import com.example.bookservice.domain.ISBN;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookJpaRepositoryAdapter implements BookRepository{
    private final BookJpaRepository jpaRepository;
    @Override
    public Book save(Book book) {
        return jpaRepository.save(book);
    }
    @Override
    public Optional<Book> findByIsbn(ISBN isbn) {
        return jpaRepository.findByIsbn(isbn);
    }
    @Override
    public Optional<Book> findById(Long id) {
        return jpaRepository.findById(id);
    }

    
}