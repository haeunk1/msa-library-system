package com.example.rentalservice.application.service.Feign;

public record MemberFeignResponse(
    Long memberId,
    Long organizationId,
    String loginId,
    String role,
    String name,
    boolean blocked) {
}
