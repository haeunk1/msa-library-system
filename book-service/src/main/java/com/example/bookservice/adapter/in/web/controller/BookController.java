package com.example.bookservice.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.bookservice.adapter.in.web.dto.RegisterBookRequest;
import com.example.bookservice.adapter.in.web.dto.UpdateBookRequest;
import com.example.bookservice.application.port.in.BookUseCase;

import lombok.RequiredArgsConstructor;

import com.example.bookservice.application.port.in.Command.RegisterBookCommand;
import com.example.bookservice.application.port.in.Command.UpdateBookCommand;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookUseCase bookUseCase;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterBookRequest request){
        RegisterBookCommand command = request.toCommand();
        Long bookId = bookUseCase.register(command);
        return ResponseEntity.ok(bookId.toString());
    }

    @PatchMapping("/location")
    public ResponseEntity<Void> updateLocation(@RequestBody UpdateBookRequest request){
        UpdateBookCommand command = request.toCommand();
        bookUseCase.updateBookLocation(command);
        return ResponseEntity.noContent().build();
    }

}
