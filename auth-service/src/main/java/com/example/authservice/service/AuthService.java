package com.example.authservice.service;

import com.example.commonlibrary.dto.request.LoginRequest;
import com.example.commonlibrary.dto.request.RefreshTokenRequest;
import com.example.commonlibrary.dto.request.RegisterRequest;
import com.example.commonlibrary.dto.response.TokenResponse;
import com.example.commonlibrary.dto.response.UserDto;

public interface AuthService {
    UserDto register(RegisterRequest request);
    TokenResponse login(LoginRequest request);
    void logout(String refreshToken);
    TokenResponse refreshToken(RefreshTokenRequest refreshToken);
}
