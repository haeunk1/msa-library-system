package com.example.bookservice.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.example.bookservice.application.port.out.BookRepository;
import com.example.bookservice.domain.Book;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookJpaRepositoryAdapter implements BookRepository{
    private final BookJpaRepository jpaRepository;
    @Override
    public Book save(Book book) {
        return jpaRepository.save(book);
    }
}
