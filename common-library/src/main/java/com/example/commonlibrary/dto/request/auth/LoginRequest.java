package com.example.commonlibrary.dto.request.auth;

public record LoginRequest(
     String email,
     String password
) { }
