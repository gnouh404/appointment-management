package com.example.authservice.service;

import com.example.authservice.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);

    Date extractExpiration(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimResolver);

    String generateToken(UserDetails userDetails);

    Claims extractAllClaims(String token);

    boolean isValidToken(String token, UserDetails userDetails);
    boolean validateToken(String token);

    boolean isTokenExpired(String token);
    String generateRefreshToken(UserDetails userDetails);
    long getExpirationTime();
    void inValidateToken(String refreshToken);
    String refreshAccessToken(String refreshToken);
    User getAuthenticatedUser();
    UserDetails extractUserDetails(String token);
    boolean isAccessToken(String token);
}
