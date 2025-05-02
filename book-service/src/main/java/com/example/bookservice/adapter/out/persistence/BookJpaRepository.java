package com.example.bookservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookservice.domain.Book;

public interface BookJpaRepository extends JpaRepository<Book, Long>{
    
}
