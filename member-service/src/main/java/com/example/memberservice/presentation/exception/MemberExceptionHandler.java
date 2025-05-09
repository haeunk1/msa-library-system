package com.example.memberservice.presentation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleException(MemberException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 또는 errorCode에 상태코드 필드를 추가
                .body(new ErrorResponse(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        log.error("Unhandled exception occurred", ex); 
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ErrorCode.UNEXPECTED_ERROR.getCode(), ErrorCode.UNEXPECTED_ERROR.getMessage()));
    }
}
