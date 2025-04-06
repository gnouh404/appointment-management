package com.example.commonlibrary.dto.request;

public record RegisterRequest(
     String email,
     String password,
     String fullName,
     String phone
) { }
