package com.example.authservice.service.user;

import com.example.authservice.entity.Permission;
import com.example.authservice.entity.Role;
import com.example.authservice.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role usersRoles : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + usersRoles.getName()));
            for (Permission permission : usersRoles.getPermissions()){
                grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }

}
