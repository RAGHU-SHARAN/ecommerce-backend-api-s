package com.backend.rscart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rscart.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
