package com.example.bookservice.domain;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.bookservice.exception.BookException;
import com.example.bookservice.exception.ErrorCode;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class BookCategory {
    String name;

    protected BookCategory(){}

    public BookCategory(String name){
        if(name == null || name.isBlank()){
            throw new BookException(ErrorCode.BOOK_CATEGORY_NAME_EMPTY);
        }
        this.name = name;
    }

    public static Set<BookCategory> of(Set<String> categories){
        return categories.stream()
        .map(BookCategory::new).collect(Collectors.toSet());
    }

}