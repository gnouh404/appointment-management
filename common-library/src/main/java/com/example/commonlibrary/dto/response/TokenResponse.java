package com.example.commonlibrary.dto.response;

public record TokenResponse(
    String accessToken,
    String refreshToken,
    Long expiresIn
) { }
