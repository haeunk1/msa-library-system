package com.example.memberservice.presentation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // 공통 에러
    UNEXPECTED_ERROR("COMMON9999", "Unexpected error"),

    // 회원 도메인 에러
    MEMBER_NOT_FOUND("MEMBER0001", "Member not found"),
    MEMBER_PASSWORD_TOO_SHORT("MEMBER0002", "비밀번호는 8자 이상이어야 합니다."),
    MEMBER_LOGIN_ID_NULL("MEMBER0003", "loginId는 null일 수 없습니다."),
    MEMBER_LOGIN_ID_LENGTH_INVALID("MEMBER0004", "loginId 길이는 5 ~ 20자 사이여야 합니다."),
    MEMBER_LOGIN_ID_ALREADY_EXIST("MEMBER0005", "이미 존재하는 ID입니다."),
    MEMBER_ROLE_CHANGE_FORBIDDEN("MEMBER0006", "회원의 역할을 변경할 권한이 없습니다."),
    MEMBER_PASSWORD_MISMATCH("MEMBER0007", "현재 비밀번호가 일치하지 않습니다."),
    MEMBER_ORGANIZATION_MISMATCH("MEMBER0008", "다른 조직의 회원은 수정할 수 없습니다.");
    ;
    private final String code;
    private final String message;
}