package com.simple.menu_api.repository;

import com.simple.menu_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRolesContaining(String role);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}