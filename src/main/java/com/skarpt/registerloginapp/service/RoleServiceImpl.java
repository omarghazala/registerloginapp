package com.skarpt.registerloginapp.service;

import com.skarpt.registerloginapp.entity.Role;
import com.skarpt.registerloginapp.entity.User;
import com.skarpt.registerloginapp.repository.RoleRepository;
import com.skarpt.registerloginapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }
}
