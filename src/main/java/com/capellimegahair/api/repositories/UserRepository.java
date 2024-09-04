package com.capellimegahair.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capellimegahair.api.entities.User;
import com.capellimegahair.api.enums.UserRole;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByRole(UserRole userRole);

    Optional<User> findFirstByEmail(String email);

    List<User> findAllByEmail(String email);

    List<User> findAllByCpf(String email);

    // Optional<User> findFirstByUsername(String username);

    List<User> findAllByRole(UserRole user);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    
    
}
