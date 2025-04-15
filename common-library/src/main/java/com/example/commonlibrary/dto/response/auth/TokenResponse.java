package com.example.commonlibrary.dto.response.auth;

public record TokenResponse(
    String accessToken,
    String refreshToken,
    Long expiresIn
) { }
