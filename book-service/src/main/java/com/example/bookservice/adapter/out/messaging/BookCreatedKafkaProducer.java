package com.example.bookservice.adapter.out.messaging;

import org.springframework.stereotype.Component;

import com.example.bookservice.application.port.out.BookMessagePort;
import com.example.bookservice.domain.event.BookCreatedMessage;
import org.springframework.kafka.core.KafkaTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BookCreatedKafkaProducer implements BookMessagePort{
    private final KafkaTemplate<String, BookCreatedMessage> kafkaTemplate;

    private String CREATE_BOOK_TOPIC = "CREATE_BOOK";
    
    public void send(BookCreatedMessage message) {
        kafkaTemplate.send(CREATE_BOOK_TOPIC, message);
    }
}
