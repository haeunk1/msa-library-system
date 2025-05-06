package com.example.bookservice.application.port.in;

import com.example.bookservice.application.port.in.Command.RegisterBookCommand;
import com.example.bookservice.application.port.in.Command.UpdateBookCommand;

public interface BookUseCase {
    Long register(RegisterBookCommand command);
    void updateBookStatus(UpdateBookCommand command);
    void updateBookLocation(UpdateBookCommand command);
    void updateBookCategory(UpdateBookCommand command);
}
