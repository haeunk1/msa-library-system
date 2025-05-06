package com.example.bookservice.application.port.in.Command;

import java.util.Set;

import com.example.bookservice.domain.BookStatus;

public record UpdateBookCommand (Long memberId, Long organizationId, Long bookId, BookStatus bookStatus, Set<String> categories, String location){
}