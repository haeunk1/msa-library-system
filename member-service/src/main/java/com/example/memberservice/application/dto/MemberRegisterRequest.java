package com.example.memberservice.application.dto;

import com.example.memberservice.domain.Role;

public record MemberRegisterRequest (
    String loginId,
    String password,
    Long organizationId,
    String name,
    Role role
){
}
