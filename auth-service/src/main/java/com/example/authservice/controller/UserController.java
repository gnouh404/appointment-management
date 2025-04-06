package com.example.authservice.controller;

import com.example.authservice.dto.UserDetailDto;
import com.example.authservice.service.user.UserService;
import com.example.commonlibrary.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDetailDto>> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserDetailDto userDto = userService.getCurrentUser();
            return ResponseEntity.ok(
                    ApiResponse.success("User fetched successfully",
                            userDto)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.fail("Something went wrong " + e.getMessage()));
        }
    }
}
