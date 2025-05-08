package com.example.bookqueryservice.presentation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookqueryservice.application.BookCatalogService;
import com.example.bookqueryservice.application.dto.BookCatalogResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookCatalog")
@RequiredArgsConstructor
public class BookCatalogController {
    private final BookCatalogService bookCatalogService;

    @GetMapping("/top-10")
    public ResponseEntity<List<BookCatalogResponse>> loadTop10Books(){
        List<BookCatalogResponse> bookCatalogs = bookCatalogService.loadTop10();
        return ResponseEntity.ok().body(bookCatalogs);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookCatalogResponse>> getBookByTitle(@PathVariable String title, Pageable pageable){
        Page<BookCatalogResponse> page = bookCatalogService.findBookByTitle(title, pageable);
        return ResponseEntity.ok().body(page.getContent());
    }
    
}
