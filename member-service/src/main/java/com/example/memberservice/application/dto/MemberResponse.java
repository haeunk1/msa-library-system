package com.example.memberservice.application.dto;

import com.example.memberservice.domain.Role;

public record MemberResponse (
    Long id,
    Long organizationId,
    String loginId,
    Role role,
    String name,
    boolean blocked
    ){
}
