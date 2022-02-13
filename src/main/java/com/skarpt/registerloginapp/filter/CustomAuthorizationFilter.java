package com.skarpt.registerloginapp.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skarpt.registerloginapp.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/user/login") || request.getServletPath().equals("/api/token/refresh") ||
                request.getServletPath().equals("/api/user/signup")){
            filterChain.doFilter(request,response);
        }
        else{
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null &&  authorizationHeader.startsWith("Bearer ")){
                try{
                    String token = authorizationHeader.substring("Bearer ".length());
                    JwtUtil jwtUtil = new JwtUtil();
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  jwtUtil.verifyToken(token);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request,response);


                }
                catch(Exception e){
                    log.error(e.getMessage());
                    response.setHeader("error",e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String,String> errors = new HashMap<>();
                    errors.put("errors",e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),errors);
                }

            }
            else{
                filterChain.doFilter(request,response);
            }
        }
    }
}
