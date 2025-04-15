package com.example.authservice.repository;

import com.example.authservice.dto.UserDetailDto;
import com.example.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    boolean existsUserByPhone(String phone);

    @Query("""
    select new com.example.authservice.dto.UserDetailDto(
        u.fullName, u.email, u.phone, r.name, p.name
    )
    from User u
    left join u.roles r
    left join r.permissions p
    where u.email = :email
    """
    )
    List<UserDetailDto> findUserWithRolesAndPermissions(@Param("email") String email);
}
