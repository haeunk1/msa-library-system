package com.example.bookservice.application.service.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "member-service", path = "/api/member")
public interface MemberFeignClient {
    @GetMapping("/{memberId}")
    MemberFeignResponse getMemberInfo(@PathVariable("memberId") Long memberId);
}
