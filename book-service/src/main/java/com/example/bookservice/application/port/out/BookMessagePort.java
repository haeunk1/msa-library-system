package com.example.bookservice.application.port.out;

import com.example.bookservice.domain.event.BookCreatedMessage;

public interface BookMessagePort {
    void send(BookCreatedMessage message);
}
