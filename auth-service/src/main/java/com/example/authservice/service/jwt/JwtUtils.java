package com.example.authservice.service.jwt;

import com.example.authservice.entity.User;
import com.example.authservice.service.JwtService;
import com.example.authservice.service.user.CustomUserDetails;
import com.example.authservice.service.user.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils implements JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private final UserDetailsServiceImpl userDetailsService;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractJti(String token) {
        return extractAllClaims(token).get("jti", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // ✅ Dùng verifyWith()
                .build()
                .parseSignedClaims(token) // ✅ Fix lỗi
                .getPayload();
    }

    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(
                        Instant.now().plus(expiration, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .claim("jti", UUID.randomUUID().toString())
                .claim("scope", buildScope(userDetails))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(
                        Instant.now().plus(7, ChronoUnit.DAYS).toEpochMilli() // 🔥 Refresh token
                ))
                .claim("jti", UUID.randomUUID().toString())
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public long getExpirationTime() {
        return expiration * 60 * 1000;
    }

    @Override
    public void inValidateToken(String refreshToken) {
        log.info("Invalidate refresh token: {}", refreshToken);
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new JwtException("Invalid refresh token");
        }

        String username = extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return generateToken(userDetails);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new RuntimeException("No authenticated user found");
        }

        return ((CustomUserDetails) authentication.getPrincipal()).getUser();
    }

    @Override
    public UserDetails extractUserDetails(String token) {
        String username = extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public boolean isAccessToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("scope") != null;
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SignatureException e){
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String buildScope(UserDetails userDetails) {
        StringJoiner joiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(userDetails.getAuthorities())) {
            userDetails.getAuthorities().forEach(role -> joiner.add(role.getAuthority()));
        }
        String scope = joiner.toString();
        return scope;
    }
}
