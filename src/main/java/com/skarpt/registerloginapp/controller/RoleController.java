package com.skarpt.registerloginapp.controller;

import com.skarpt.registerloginapp.dto.RoleToUser;
import com.skarpt.registerloginapp.entity.Role;
import com.skarpt.registerloginapp.service.RoleService;
import com.skarpt.registerloginapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Slf4j
public class RoleController {
    @Autowired
    RoleService roleService;
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(null).body(roleService.saveRole(role));
    }

    @PostMapping("/role/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser roleToUser){
        roleService.addRoleToUser(roleToUser.getEmail(), roleToUser.getRoleName());
        return ResponseEntity.ok().build();
    }
}
