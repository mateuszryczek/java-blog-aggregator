package com.mime.jba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mime.jba.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String string);

}
