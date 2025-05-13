package com.example.bookservice.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.Set;

import com.example.bookservice.application.port.in.Command.RegisterBookCommand;
import com.example.bookservice.domain.BookCategory;
import com.example.bookservice.domain.BookStatus;
import com.example.bookservice.domain.ISBN;

public record RegisterBookRequest (
    Long memberId,
    Long organizationId,
    String title,
    String author,
    String publisher,
    String isbn,
    String publicationDate,
    Set<String> categories,
    String bookStatus,
    String location
){
    public RegisterBookCommand toCommand() {
    return new RegisterBookCommand(
        memberId,
        organizationId,
        title,
        author,
        publisher,
        ISBN.of(isbn),
        LocalDate.parse(publicationDate),
        BookCategory.of(categories),
        BookStatus.valueOf(bookStatus),
        location
    );
}
}
