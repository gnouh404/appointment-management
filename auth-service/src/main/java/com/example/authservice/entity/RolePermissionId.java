package com.example.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
public class RolePermissionId {
    @Column(name= "role_id")
    private Integer roleId;

    @Column(name = "permission_id")
    private Integer permissionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolePermissionId other = (RolePermissionId) o;
        return Objects.equals(this.roleId, other.roleId) && Objects.equals(this.permissionId, other.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId);
    }
}
