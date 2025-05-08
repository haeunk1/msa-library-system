package com.example.bookqueryservice.application;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.bookqueryservice.application.dto.BookCatalogResponse;
import com.example.bookqueryservice.application.message.BookCreatedMessage;
import com.example.bookqueryservice.domain.BookCatalog;
import com.example.bookqueryservice.domain.BookCatalogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookCatalogService {
    private final BookCatalogRepository bookCatalogRepository;

    public Page<BookCatalogResponse> findBookByTitle(String title, Pageable pageable){
        Page<BookCatalog> bookCatalogPage = bookCatalogRepository.findByTitleContaining(title,pageable);
        return bookCatalogPage.map(BookCatalogResponse::from);
    }

    public List<BookCatalogResponse> loadTop10(){
        List<BookCatalog> bookCatalogList = bookCatalogRepository.findTop10ByOrderByRentCntDesc();
        return bookCatalogList.stream().map(BookCatalogResponse::from).collect(Collectors.toList());
    }

    public void save(BookCreatedMessage message) {
        BookCatalog catalog = BookCatalog.from(message);
        bookCatalogRepository.save(catalog);
    }
}
