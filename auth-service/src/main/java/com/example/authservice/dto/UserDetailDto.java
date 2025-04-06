package com.example.authservice.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserDetailDto{
    private String fullName;
    private String email;
    private String phone;
    private List<String> roles;
    private List<String> permissions;

    public UserDetailDto(String fullName, String email, String phone, String role, String permission) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.roles = role != null ? new ArrayList<>(List.of(role)) : new ArrayList<>();
        this.permissions = permission != null ? new ArrayList<>(List.of(permission)) : new ArrayList<>();
    }

    public void addRole(String role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    public void addPermission(String permission) {
        if (!permissions.contains(permission)) {
            permissions.add(permission);
        }
    }
}

