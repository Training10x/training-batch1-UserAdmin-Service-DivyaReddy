package com.example.userapp.repository;

import com.example.userapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    Page<User> findByFirstNameContainingAndLastNameContainingAndPhoneContainingAndRoleContaining(
            String firstName, String lastName, String phone, String role, Pageable pageable);
}
