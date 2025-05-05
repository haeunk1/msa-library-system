package com.example.bookservice.application.service.feign;

public record MemberFeignResponse(
    Long memberId,
    Long organizationId,
    String loginId,
    String role,
    String name,
    boolean blocked) {
}