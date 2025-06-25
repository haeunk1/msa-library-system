package com.example.memberservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.memberservice.config.jwt.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {
        http
            .csrf().disable()  // CSRF 보호 비활성화
            .headers().frameOptions().disable()
            .and()
            .authorizeHttpRequests()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/member/register").permitAll()  // 회원가입 API 인증 없이 허용
                .requestMatchers("/api/member/login").permitAll()  
                .requestMatchers("/api/member/**")
                    .access((authentication, context) -> {
                        // 헤더 확인해서 내부 요청이면 허용
                        HttpServletRequest request = context.getRequest();
                        String internal = request.getHeader("X-Internal-Call");
                        if ("true".equals(internal)) {
                            return new AuthorizationDecision(true);
                        }
                        return new AuthorizationDecision(authentication.get() != null && authentication.get().isAuthenticated());
                    })
                .anyRequest().authenticated()  // 나머지 요청은 인증 필수
            .and()
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // JWT 필터 등록
            .formLogin().disable();  // 폼 로그인 비활성화
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(@Value("${jwt.jwtSecret}") String jwtSecret) {
        return new JwtAuthenticationFilter(jwtSecret);
    }
}