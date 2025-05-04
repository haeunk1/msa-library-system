package com.example.bookservice.application.port.in;

import com.example.bookservice.application.port.in.Command.RegisterBookCommand;
import com.example.bookservice.domain.BookStatus;

public interface BookUseCase {
    Long register(RegisterBookCommand command);
    void changeBookStatus(String isbn, BookStatus bookstatus);
    void changeBookLocation(String isbn, String location);
}
