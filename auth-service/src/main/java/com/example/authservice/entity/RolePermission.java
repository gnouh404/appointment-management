package com.example.authservice.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_permissions", schema = "auth")
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;

    public RolePermission(){
        // no args constructor
    }

    public RolePermission(RolePermissionId id){
        this.id = id;
    }

    public int getRoleId(){ return id.getRoleId(); }

    public int getPermissionId(){ return id.getPermissionId(); }
}
