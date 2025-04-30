package com.example.memberservice.application.dto;

public record LoginRequest(
    String loginId, String password
) {}
