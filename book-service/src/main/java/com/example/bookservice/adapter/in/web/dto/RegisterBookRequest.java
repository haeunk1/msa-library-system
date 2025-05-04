package com.example.bookservice.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.Set;

import com.example.bookservice.application.port.in.Command.RegisterBookCommand;
import com.example.bookservice.domain.BookCategory;
import com.example.bookservice.domain.BookStatus;

public record RegisterBookRequest (
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
        organizationId,
        title,
        author,
        publisher,
        isbn,
        LocalDate.parse(publicationDate),
        BookCategory.of(categories),
        BookStatus.valueOf(bookStatus),
        location
    );
}
}
