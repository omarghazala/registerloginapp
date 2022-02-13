package com.skarpt.registerloginapp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class JwtUtil {

    public static Map<String,String> tokens = new HashMap<>();

    public String generateToken(User user, Algorithm algorithm, HttpServletRequest request){
        String access_token = String.valueOf(JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 10*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm));
        return access_token;
    }

    public String generateTokenCustom(String email, List<GrantedAuthority> grantedAuths, Algorithm algorithm, HttpServletRequest request){
        String access_token = String.valueOf(JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis()+ 10*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",grantedAuths.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm));
        return access_token;
    }

    public String generateRefreshToken(User user, Algorithm algorithm, HttpServletRequest request){
        String refresh_token = String.valueOf(JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 30*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm));
        return refresh_token;
    }

    public String generateRefreshTokenCustom(String email, Algorithm algorithm, HttpServletRequest request){
        String refresh_token = String.valueOf(JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis()+ 30*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm));
        return refresh_token;
    }

    public UsernamePasswordAuthenticationToken verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String email = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role ->{
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return  new UsernamePasswordAuthenticationToken(email,null,authorities);
    }
}
