package com.example.apigateway.filter;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtValidator {

    @Value("${jwt.jwtSecret}")
    private String jwtSecretKey;

    private SecretKey secretKey;
    
    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 64) {
            throw new IllegalArgumentException("JWT key must be at least 64 bytes for HS512.");
        }
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean isValid(String token) {
        try {
            Jwts.parser()
            .setSigningKey(secretKey).build()
            .parseClaimsJws(token);

            return true;
        
        }catch(JwtException jwtException){
            log.warn("JWT validation error: {}", jwtException.getMessage());
            if (jwtException instanceof ExpiredJwtException) {
                log.warn("Expired JWT token: {}", token);
            } else if (jwtException instanceof MalformedJwtException) {
                log.warn("Malformed JWT token: {}", token);
            } else {
                log.warn("Other JWT error: {}", jwtException.getClass().getSimpleName());
            }
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parser()  
            .setSigningKey(secretKey) 
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
