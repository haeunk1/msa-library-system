package com.example.memberservice.domain.vo;

import java.util.Objects;

import com.example.memberservice.presentation.exception.ErrorCode;
import com.example.memberservice.presentation.exception.MemberException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class LoginId {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 20;

    @Column(nullable=false)
    private String loginId;
    
    public static LoginId of(String loginId){
        validate(loginId);
        return new LoginId(loginId);
    }

    private static void validate(String loginId) {
        if (Objects.isNull(loginId)) {
            throw new MemberException(ErrorCode.MEMBER_LOGIN_ID_NULL);
        }
        if (loginId.length() < MIN_LENGTH || loginId.length() > MAX_LENGTH) {
            throw new MemberException(ErrorCode.MEMBER_LOGIN_ID_LENGTH_INVALID);
        }
    }
}
