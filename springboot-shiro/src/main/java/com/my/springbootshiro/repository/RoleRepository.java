package com.my.springbootshiro.repository;

import com.my.springbootshiro.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
