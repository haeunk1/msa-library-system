package com.example.bookqueryservice.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.example.bookqueryservice.domain.BookCatalog;
import com.example.bookqueryservice.domain.BookCatalogRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookCatalogRepositoryImpl implements BookCatalogRepository{

    private final BookCatalogMongoRepository mongoRepository;

    @Override
    public Page<BookCatalog> findByTitleContaining(String title, Pageable pageable) {
        return mongoRepository.findByTitleContaining(title, pageable);
    }

    @Override
    public List<BookCatalog> findTop10ByOrderByRentCntDesc() {
        return mongoRepository.findTop10ByOrderByRentCntDesc();
    }

    @Override
    public void save(BookCatalog bookCatalog){
        mongoRepository.save(bookCatalog);
    }


}
