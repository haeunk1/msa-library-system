package com.example.bookservice.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookservice.domain.Book;

public interface BookJpaRepository extends JpaRepository<Book, Long>{
    Optional<Book> findByIsbn(String isbn);
}
