package com.skarpt.registerloginapp.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.skarpt.registerloginapp.entity.Role;
import com.skarpt.registerloginapp.entity.User;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,String> saveUser(User user, HttpServletRequest request, HttpServletResponse response) throws IOException;
    User getUser(String email);
    List<User> getUsers();
    Map<String,String> authWithHttpAuthManager(HttpServletRequest request, HttpServletResponse response, String email, String password, List<GrantedAuthority> grantedAuthorities) throws IOException;
    void logOut();
}
