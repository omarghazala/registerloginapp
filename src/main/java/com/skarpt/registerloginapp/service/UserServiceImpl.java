package com.skarpt.registerloginapp.service;


import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skarpt.registerloginapp.entity.Role;
import com.skarpt.registerloginapp.entity.User;
import com.skarpt.registerloginapp.repository.RoleRepository;
import com.skarpt.registerloginapp.repository.UserRepository;
import com.skarpt.registerloginapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import javax.management.remote.JMXAuthenticator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService, AuthenticationProvider {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private Map<String,String> tokens = new HashMap<>();


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            System.out.println("User not found !");
            throw new UsernameNotFoundException("User not found !");
        }
        else{
            System.out.println("User found !");

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    @Override
    public Map<String,String> saveUser(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(userRepository.existsByEmail(user.getEmail())){
            log.info("User found");
            return null;
        }
        else{
            String password = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            //Collection<GrantedAuthority> authorities = new ArrayList<>();
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));

            userRepository.save(user);
            return authWithHttpAuthManager(request,response,user.getEmail(),password,grantedAuths);

        }
    }



    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    public Map<String,String> authWithHttpAuthManager(HttpServletRequest request,HttpServletResponse response, String email, String password, List<GrantedAuthority> grantedAuths ) throws IOException {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = this.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(authentication.isAuthenticated()){

            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JwtUtil jwtUtil = new JwtUtil();

            String access_token = jwtUtil.generateTokenCustom(email,grantedAuths,algorithm,request);
            String refresh_token = jwtUtil.generateRefreshTokenCustom(email,algorithm,request);


            this.tokens.put("access_token",access_token);
            this.tokens.put("refresh_token",refresh_token);
            response.setContentType(APPLICATION_JSON_VALUE);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(),this.tokens);
            return tokens;

        }
        return null;

    }

    @Override
    public void logOut() {
        JwtUtil.tokens = null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String email = authentication.getName();
            String password = authentication.getCredentials().toString();
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }


}
