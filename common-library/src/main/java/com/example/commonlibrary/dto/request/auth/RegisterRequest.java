package com.example.commonlibrary.dto.request.auth;

public record RegisterRequest(
     String email,
     String password,
     String fullName,
     String phone
) { }
