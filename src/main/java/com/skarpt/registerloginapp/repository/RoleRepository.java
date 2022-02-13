package com.skarpt.registerloginapp.repository;

import com.skarpt.registerloginapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(String name);
}
