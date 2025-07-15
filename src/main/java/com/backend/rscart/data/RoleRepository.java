package com.backend.rscart.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rscart.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}
