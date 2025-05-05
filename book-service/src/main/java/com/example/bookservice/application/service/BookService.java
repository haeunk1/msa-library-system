package com.example.bookservice.application.service;

import com.example.bookservice.application.port.out.BookRepository;
import com.example.bookservice.application.service.feign.MemberFeignClient;
import com.example.bookservice.application.service.feign.MemberFeignResponse;
import com.example.bookservice.domain.Book;
import com.example.bookservice.domain.BookStatus;

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
            throw new IllegalArgumentException("권한이 없습니다.");
        }

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
