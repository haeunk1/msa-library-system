package com.example.bookservice.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bookservice.domain.Book;
import com.example.bookservice.domain.ISBN;

public interface BookJpaRepository extends JpaRepository<Book, Long>{

    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
    Optional<Book> findByIsbn(@Param("isbn") ISBN isbn);
}
