package com.example.authservice.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users_roles", schema = "auth")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    public UserRole() {}

    public UserRole(UserRoleId id) {
        this.id = id;
    }

    public Long getUserId() {
        return id.getUserId();
    }

    public int getRoleId() {
        return id.getRoleId();
    }
}
