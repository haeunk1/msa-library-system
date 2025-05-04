package com.example.bookservice.domain;

import java.util.Set;
import java.util.stream.Collectors;

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
            throw new IllegalArgumentException("카테고리 이름은 비어 있을 수 없습니다.");
        }
        this.name = name;
    }

    public static Set<BookCategory> of(Set<String> categories){
        return categories.stream()
        .map(BookCategory::new).collect(Collectors.toSet());
    }

}