package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.config.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
