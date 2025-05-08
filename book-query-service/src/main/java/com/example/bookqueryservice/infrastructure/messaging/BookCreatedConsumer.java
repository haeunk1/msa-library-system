package com.example.bookqueryservice.infrastructure.messaging;

import org.springframework.stereotype.Component;

import com.example.bookqueryservice.application.BookCatalogService;
import com.example.bookqueryservice.application.message.BookCreatedMessage;
import org.springframework.kafka.annotation.KafkaListener;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookCreatedConsumer {

    private final BookCatalogService bookCatalogService;

    @KafkaListener(topics = "CREATE_BOOK", groupId = "bookquery")
    public void consume(BookCreatedMessage message) {
        bookCatalogService.save(message);
    }
}