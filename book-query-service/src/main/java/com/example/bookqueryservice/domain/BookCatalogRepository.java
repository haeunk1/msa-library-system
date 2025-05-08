package com.example.bookqueryservice.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface BookCatalogRepository {
    Page<BookCatalog> findByTitleContaining(String title, Pageable Pageable);

    List<BookCatalog> findTop10ByOrderByRentCntDesc();

    void save(BookCatalog bookCatalog);
}
