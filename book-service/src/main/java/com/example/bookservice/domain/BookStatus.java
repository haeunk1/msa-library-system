package com.example.bookservice.domain;

public enum BookStatus {
    AVAILABLE,         // 대출 가능
    ON_LOAN,           // 대출 중
    LOST,              // 분실 처리됨
    DAMAGED,           // 훼손됨
    DISCARDED,         // 폐기됨 (도서관에서 제거)
    UNAVAILABLE        // 임시 이용 불가 (전시, 점검 등)
}
