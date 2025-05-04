package com.example.bookservice.application.service;

import com.example.bookservice.application.port.out.BookRepository;
import com.example.bookservice.domain.Book;
import com.example.bookservice.domain.BookStatus;

import lombok.RequiredArgsConstructor;

import org.h2.api.ErrorCode;
import org.springframework.stereotype.Service;

import com.example.bookservice.application.port.in.BookUseCase;
import com.example.bookservice.application.port.in.Command.RegisterBookCommand;

@RequiredArgsConstructor
@Service
public class BookService implements BookUseCase{
    private final BookRepository bookRepository;

    @Override
    public Long register(RegisterBookCommand command) {
        bookRepository.findByIsbn(command.isbn())
        .ifPresent(b -> {
            throw new IllegalArgumentException("이미 등록된 ISBN입니다: " + b.getIsbn());
        });

        Book newBook = Book.of(command);
        return bookRepository.save(newBook).getId();
    }

    @Override
    public void changeBookStatus(String isbn, BookStatus bookstatus) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 도서입니다."));
        book.changeBookStatus(bookstatus);
        bookRepository.save(book);
    }

    @Override
    public void changeBookLocation(String isbn,String location) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 도서입니다."));
        book.changeBookLocation(location);
        bookRepository.save(book);
    }
    
}
