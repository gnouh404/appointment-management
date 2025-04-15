package com.example.commonlibrary.dto.response.auth;

public record UserDto(
    String fullName,
    String email,
    String phone
) { }
