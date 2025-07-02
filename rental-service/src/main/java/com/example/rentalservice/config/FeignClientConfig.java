package com.example.rentalservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor internalCallHeaderInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 내부 서비스 간 호출임을 명시하는 커스텀 헤더 추가
                template.header("X-Internal-Call", "true");
            }
        };
    }
}