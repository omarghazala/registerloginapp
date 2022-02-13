package com.skarpt.registerloginapp.service;

import com.skarpt.registerloginapp.entity.Role;

public interface RoleService {
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
}
