package com.example.authservice.service.user;

import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.jwt.JwtUtils;
import com.example.commonlibrary.dto.request.auth.LoginRequest;
import com.example.commonlibrary.dto.request.auth.RefreshTokenRequest;
import com.example.commonlibrary.dto.request.auth.RegisterRequest;
import com.example.commonlibrary.dto.response.auth.TokenResponse;
import com.example.commonlibrary.dto.response.auth.UserDto;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Transactional(rollbackOn = {DataIntegrityViolationException.class, ConstraintViolationException.class})
    @Override
    public UserDto register(RegisterRequest request) {
        if (userRepository.existsUserByEmail(request.email())) {
            throw new DataIntegrityViolationException("Email already exists: " + request.email());
        }

        if (userRepository.existsUserByPhone(request.phone())) {
            throw new DataIntegrityViolationException("Phone already exists: " + request.phone());
        }

        User user = new User();
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPhone(request.phone());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setIsActive(true);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        userRepository.save(user);
        return new UserDto(user.getFullName(), user.getEmail(), user.getPhone());
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        UserDetails userDetails = new CustomUserDetails(user);

        String accessToken = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);
        long expiresIn = jwtUtils.getExpirationTime();

        return new TokenResponse(accessToken, refreshToken, expiresIn);
    }

    @Override
    public void logout(String refreshToken) {
        jwtUtils.inValidateToken(refreshToken);
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        String newAccessToken = jwtUtils.refreshAccessToken(request.refreshToken());
        UserDetails userDetails = jwtUtils.extractUserDetails(request.refreshToken());
        String newRefreshToken = jwtUtils.generateRefreshToken(userDetails);
        long expiresIn = jwtUtils.getExpirationTime();

        return new TokenResponse(newAccessToken, newRefreshToken, expiresIn);
    }
}
