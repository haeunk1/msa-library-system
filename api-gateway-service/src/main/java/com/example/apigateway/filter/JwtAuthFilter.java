package com.example.apigateway.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    private final JwtValidator jwtValidator;

    public JwtAuthFilter(JwtValidator jwtValidator) {
        super(Config.class);
        this.jwtValidator = jwtValidator;
    }

    public static class Config {
    }
    private static final List<String> excludePaths = List.of(
        "/api/member/login",
        "/api/member/register"
    );

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String path = exchange.getRequest().getURI().getPath();
            if (excludePaths.contains(path)) {
                return chain.filter(exchange);
            }
            ServerHttpRequest request = exchange.getRequest();
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Authorization 헤더 없음 또는 형식 오류");
                return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            if (!jwtValidator.isValid(token)) {
                log.warn("유효하지 않은 JWT 토큰");
                return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
            }

            // (선택) 토큰에서 정보 꺼내기 - 나중에 헤더에 추가 가능
            Claims claims = jwtValidator.getClaims(token);
            String memberId = claims.getSubject();
            String role = claims.get("role", String.class);

            // (선택) 요청에 사용자 정보 추가
            ServerHttpRequest mutatedRequest = request.mutate()
                .header("X-Member-Id", memberId)
                .header("X-Member-Role", role)
                .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        log.warn("JWT 인증 실패: {}", message);
        return response.setComplete();
    }
}