package com.example.bookqueryservice.infrastructure;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.bookqueryservice.domain.BookCatalog;

public interface BookCatalogJpaRepository extends MongoRepository<BookCatalog,Long>{
}
