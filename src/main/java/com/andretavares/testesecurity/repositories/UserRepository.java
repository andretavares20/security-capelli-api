package com.andretavares.testesecurity.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.enums.UserRole;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByRole(UserRole userRole);

    Optional<User> findFirstByEmail(String email);

    List<User> findAllByEmail(String email);

    // Optional<User> findFirstByUsername(String username);

    List<User> findAllByRole(UserRole user);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    
}
