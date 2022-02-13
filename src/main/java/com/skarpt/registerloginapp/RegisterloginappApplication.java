package com.skarpt.registerloginapp;

import com.skarpt.registerloginapp.entity.Role;
import com.skarpt.registerloginapp.entity.User;
import com.skarpt.registerloginapp.service.RoleService;
import com.skarpt.registerloginapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@SpringBootApplication
public class RegisterloginappApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterloginappApplication.class, args);
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(RoleService roleService){
		return args -> {
			roleService.saveRole(new Role(1,"ROLE_USER"));

//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//			userService.saveUser(new User(1,"omar ghazala","omarghazala@gmail.com","1234", simpleDateFormat.parse("12/12/1994"),new ArrayList<>()));
//			userService.saveUser(new User(2,"nada ghazala","nadaghazala@gmail.com","1234", simpleDateFormat.parse("12/12/1994"),new ArrayList<>()));
//
//			userService.addRoleToUser("omarghazala@gmail.com","ROLE_USER");
//			userService.addRoleToUser("omarghazala@gmail.com","ROLE_ADMIN");
//			userService.addRoleToUser("nadaghazala@gmail.com","ROLE_USER");
		};
	}
}
