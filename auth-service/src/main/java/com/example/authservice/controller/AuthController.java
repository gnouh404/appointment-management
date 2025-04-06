package com.example.authservice.controller;

import com.example.authservice.service.AuthService;
import com.example.commonlibrary.dto.request.LoginRequest;
import com.example.commonlibrary.dto.request.RefreshTokenRequest;
import com.example.commonlibrary.dto.request.RegisterRequest;
import com.example.commonlibrary.dto.response.ApiResponse;
import com.example.commonlibrary.dto.response.TokenResponse;
import com.example.commonlibrary.dto.response.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody RegisterRequest request) {
        try {
            UserDto userDto = authService.register(request);
            return ResponseEntity.ok(
                    ApiResponse.success("User registered successfully",
                                userDto)
            );
        } catch (DataIntegrityViolationException e) {
            log.error("Violate", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail(e.getMessage()));
        } catch (Exception e){
            log.error("Unexpected error in register method", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Something went wrong " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        try {
            TokenResponse tokenResponse = authService.login(request);
            return ResponseEntity.ok(
                    ApiResponse.success("User logged in successfully",
                            tokenResponse)
            );
        } catch (UsernameNotFoundException e){
            log.error("User not found", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail(e.getMessage()));
        } catch (BadCredentialsException e){
            log.error("Bad credentials", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail(e.getMessage()));
        } catch (Exception e){
            log.error("Unexpected error in login method", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Something went wrong " + e.getMessage()));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(@RequestBody RefreshTokenRequest request){
        try {
            TokenResponse tokenResponse = authService.refreshToken(request);
            return ResponseEntity.ok(
                    ApiResponse.success("Token refreshed successfully",
                            tokenResponse)
            );
        } catch (Exception e){
            log.error("Unexpected error in refresh method", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Something went wrong " + e.getMessage()));
        }
    }
}
