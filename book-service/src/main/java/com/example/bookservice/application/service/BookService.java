package com.example.bookservice.application.service;

import com.example.bookservice.application.port.out.BookRepository;
import com.example.bookservice.application.service.feign.MemberFeignClient;
import com.example.bookservice.application.service.feign.MemberFeignResponse;
import com.example.bookservice.domain.Book;
import com.example.bookservice.domain.BookStatus;
import com.example.bookservice.exception.ErrorCode;
import com.example.bookservice.exception.BookException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookservice.application.port.in.BookUseCase;
import com.example.bookservice.application.port.in.Command.RegisterBookCommand;

@RequiredArgsConstructor
@Service
@Transactional
public class BookService implements BookUseCase{
    private final BookRepository bookRepository;
    private final MemberFeignClient memberFeignClient;

    @Override
    public Long register(RegisterBookCommand command) {
        MemberFeignResponse memberFeignResponse = memberFeignClient.getMemberInfo(command.memberId());
        String role = memberFeignResponse.role();
        if(!"ADMIN".equals(role)){
            throw new BookException(ErrorCode.BOOK_ROLE_UNAUTHORIZED);
        }

        bookRepository.findByIsbn(command.isbn())
        .ifPresent(b -> {
            throw new BookException(ErrorCode.BOOK_ISBN_ALREADY_EXIST);
        });

        Book newBook = Book.of(command);
        return bookRepository.save(newBook).getId();
    }

    @Override
    public void changeBookStatus(String isbn, BookStatus bookstatus) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookException(ErrorCode.BOOK_NOT_FOUND));
        book.changeBookStatus(bookstatus);
        bookRepository.save(book);
    }

    @Override
    public void changeBookLocation(String isbn,String location) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookException(ErrorCode.BOOK_NOT_FOUND));
        book.changeBookLocation(location);
        bookRepository.save(book);
    }
    
}
