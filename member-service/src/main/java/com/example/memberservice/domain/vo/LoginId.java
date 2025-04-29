package com.example.memberservice.domain.vo;

import java.util.Objects;

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
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 12;

    @Column(nullable=false)
    private String loginId;
    
    public static LoginId of(String loginId){
        validate(loginId);
        return new LoginId(loginId);
    }

    private static void validate(String loginId) {
        if (Objects.isNull(loginId)) {
            throw new IllegalArgumentException("loginId는 null일 수 없습니다.");
        }
        if (loginId.length() < MIN_LENGTH || loginId.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("loginId 길이는 " + MIN_LENGTH + " ~ " + MAX_LENGTH + " 사이여야 합니다.");
        }
    }
}
