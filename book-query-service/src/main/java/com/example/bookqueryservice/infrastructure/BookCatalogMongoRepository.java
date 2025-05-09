package com.example.bookqueryservice.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.bookqueryservice.domain.BookCatalog;

@Repository
public interface BookCatalogMongoRepository extends MongoRepository<BookCatalog,Long>{
    Page<BookCatalog> findByTitleContaining(String title, Pageable Pageable);

    List<BookCatalog> findTop10ByOrderByRentCntDesc();
}
