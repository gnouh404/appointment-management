package com.example.authservice.service;

import com.example.commonlibrary.dto.request.auth.LoginRequest;
import com.example.commonlibrary.dto.request.auth.RefreshTokenRequest;
import com.example.commonlibrary.dto.request.auth.RegisterRequest;
import com.example.commonlibrary.dto.response.auth.TokenResponse;
import com.example.commonlibrary.dto.response.auth.UserDto;

public interface AuthService {
    UserDto register(RegisterRequest request);
    TokenResponse login(LoginRequest request);
    void logout(String refreshToken);
    TokenResponse refreshToken(RefreshTokenRequest refreshToken);
}
