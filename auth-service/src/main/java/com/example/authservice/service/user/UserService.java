package com.example.authservice.service.user;

import com.example.authservice.dto.UserDetailDto;
import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.service.jwt.JwtUtils;
import com.example.commonlibrary.dto.response.UserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Transactional
    public UserDetailDto getCurrentUser() {
        String email = jwtUtils.getAuthenticatedUser().getEmail();
        List<UserDetailDto> result = userRepository.findUserWithRolesAndPermissions(email);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDetailDto userDto = new UserDetailDto(result.get(0).getFullName(),
                result.get(0).getEmail(), result.get(0).getPhone(), null, null);

        for (UserDetailDto dto : result){
            if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
                userDto.addRole(dto.getRoles().get(0));
            }
            if (dto.getPermissions() != null && !dto.getPermissions().isEmpty()) {
                userDto.addPermission(dto.getPermissions().get(0));
            }
        }

        return userDto;
    }
}
