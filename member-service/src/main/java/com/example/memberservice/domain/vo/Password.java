package com.example.memberservice.domain.vo;

import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Password {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Column(nullable = false)
    private String password;

    public static Password of(String password) {
        validate(password);
        return new Password(encoder.encode(password));
    }

    private static void validate(final String password) {
        if (Objects.isNull(password) || password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");
        }
    }

    public boolean isMatch(String pw){
        return encoder.matches(pw, this.password);
    }
}
