package com.example.bookservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // 공통 에러
    UNEXPECTED_ERROR("COMMON9999", "Unexpected error"),

    // 도서서 도메인 에러
    BOOK_ROLE_UNAUTHORIZED("BOOK0001", "권한이 없습니다."),
    BOOK_ISBN_ALREADY_EXIST("BOOK0002", "이미 등록된 ISBN입니다."),
    BOOK_NOT_FOUND("BOOK0003", "존재하지 않는 도서입니다."),
    BOOK_ISBN_INVALID("BOOK0004", "유효하지 않은 ISBN입니다."),
    BOOK_CATEGORY_REQUIRED("BOOK0005", "하나 이상의 카테고리가 입력되어야 합니다."),
    BOOK_CATEGORY_NAME_EMPTY("BOOK0006", "카테고리 이름은 비어 있을 수 없습니다.");
    ;
    private final String code;
    private final String message;
}