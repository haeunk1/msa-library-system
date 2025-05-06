package com.example.bookservice.adapter.in.web.dto;

import java.util.Set;


import com.example.bookservice.application.port.in.Command.UpdateBookCommand;
import com.example.bookservice.domain.BookStatus;

public record UpdateBookRequest(
    Long memberId, 
    Long organizationId,
    Long bookId, 
    BookStatus bookStatus, 
    Set<String> categories, 
    String location) 
{
    public UpdateBookCommand toCommand(){
        return new UpdateBookCommand(memberId, organizationId, bookId, bookStatus,categories,location);
    }
}
