package com.example.bookservice.application.service;

import com.example.bookservice.application.port.out.BookRepository;
import com.example.bookservice.application.service.feign.MemberFeignClient;
import com.example.bookservice.application.service.feign.MemberFeignResponse;
import com.example.bookservice.domain.Book;
import com.example.bookservice.domain.BookCategory;
import com.example.bookservice.domain.BookStatus;
import com.example.bookservice.exception.ErrorCode;
import com.example.bookservice.exception.BookException;

import lombok.RequiredArgsConstructor;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookservice.application.port.in.BookUseCase;
import com.example.bookservice.application.port.in.Command.RegisterBookCommand;
import com.example.bookservice.application.port.in.Command.UpdateBookCommand;

@RequiredArgsConstructor
@Service
@Transactional
public class BookService implements BookUseCase{
    private final BookRepository bookRepository;
    private final MemberFeignClient memberFeignClient;

    // 권한체크 & 소속체크
    private void validateAdmin(Long memberId, Long organizationId) {
        MemberFeignResponse memberFeignResponse = memberFeignClient.getMemberInfo(memberId);
        String role = memberFeignResponse.role();
        if(!"ADMIN".equals(role)){
            throw new BookException(ErrorCode.BOOK_ROLE_UNAUTHORIZED);
        }

        if(!memberFeignResponse.organizationId().equals(organizationId)){
            throw new BookException(ErrorCode.BOOK_DIFFRENT_ORGANIZATION);
        }
    }

    @Override
    public Long register(RegisterBookCommand command) {
        validateAdmin(command.memberId(), command.organizationId());

        bookRepository.findByIsbn(command.isbn())
        .ifPresent(b -> {
            throw new BookException(ErrorCode.BOOK_ISBN_ALREADY_EXIST);
        });

        Book newBook = Book.of(command);
        return bookRepository.save(newBook).getId();
    }

    @Override
    public void updateBookStatus(UpdateBookCommand command) {
        if(command.memberId() == null && command.bookId() == null && command.bookStatus() == null){
            throw new BookException(ErrorCode.BOOK_UPDATE_INFO_NOT_CORRECT);
        }
        validateAdmin(command.memberId(),command.organizationId());

        Book book = bookRepository.findById(command.bookId()).orElseThrow(() -> new BookException(ErrorCode.BOOK_NOT_FOUND));
        book.updateBookStatus(command.bookStatus());
        bookRepository.save(book);
    }

    @Override
    public void updateBookLocation(UpdateBookCommand command) {
        if(command.memberId() == null && command.bookId() == null && command.location() == null){
            throw new BookException(ErrorCode.BOOK_UPDATE_INFO_NOT_CORRECT);
        }
        validateAdmin(command.memberId(),command.organizationId());

        Book book = bookRepository.findById(command.bookId()).orElseThrow(() -> new BookException(ErrorCode.BOOK_NOT_FOUND));
        book.updateBookLocation(command.location());
        bookRepository.save(book);
    }

    @Override
    public void updateBookCategory(UpdateBookCommand command){
        if(command.memberId() == null && command.bookId() == null && command.categories() == null){
            throw new BookException(ErrorCode.BOOK_UPDATE_INFO_NOT_CORRECT);
        }
        validateAdmin(command.memberId(),command.organizationId());

        Book book = bookRepository.findById(command.bookId()).orElseThrow(() -> new BookException(ErrorCode.BOOK_NOT_FOUND));
        Set<BookCategory> bookCategories = BookCategory.of(command.categories());
        book.updateCategories(bookCategories);
        bookRepository.save(book);
    }
    
}
