package com.example.memberservice.config.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.jwtSecret}")
    private String jwtSecretKey;
    
    @Value("${jwt.jwtExpirationMs}")
    private int jwtExpirationMs;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        // jwtSecretKey를 바이트 배열로 변환하고, 이를 사용하여 HMAC-SHA256 알고리즘에 사용할 키를 생성한다.
        byte[] keyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("The key length should be at least 32 bytes");
        }
        secretKey = Keys.hmacShaKeyFor(keyBytes); // Secret Key 생성
    }

    public String generateAccessToken(Long memberId, String name) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        // JWT 토큰 생성
        return Jwts.builder()
                .subject(memberId.toString())
                .claim("name", name)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }
}
