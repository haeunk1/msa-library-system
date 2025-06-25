package com.example.bookservice.application.service.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.bookservice.config.FeignClientConfig;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "member-service",
    path = "/api/member",
    configuration = FeignClientConfig.class
)
public interface MemberFeignClient {
    @GetMapping("/{memberId}")
    MemberFeignResponse getMemberInfo(@PathVariable("memberId") Long memberId);
}
