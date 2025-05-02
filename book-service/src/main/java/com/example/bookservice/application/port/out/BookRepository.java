package com.example.bookservice.application.port.out;

import com.example.bookservice.domain.Book;

public interface BookRepository{
    Book save(Book book);
}
