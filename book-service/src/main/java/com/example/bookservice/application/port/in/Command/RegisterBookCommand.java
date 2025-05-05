package com.example.bookservice.application.port.in.Command;

import java.time.LocalDate;
import java.util.Set;

import com.example.bookservice.domain.BookCategory;
import com.example.bookservice.domain.BookStatus;


public record RegisterBookCommand(
    Long memberId,
    Long organizationId,
    String title,
    String author,
    String publisher,
    String isbn,
    LocalDate publicationDate,
    Set<BookCategory> categories,
    BookStatus bookStatus,
    String location
) {
   
}
