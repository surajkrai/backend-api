package com.surajrai.blog.backendapi;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.surajrai.blog.backendapi.config.JavaConstants;
import com.surajrai.blog.backendapi.entities.Role;
import com.surajrai.blog.backendapi.repo.RolesRepo;

@SpringBootApplication
public class BackendApiApplication implements CommandLineRunner {

	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RolesRepo rolesRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BackendApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("childish"));
		
		Role adminRole=new Role();
		adminRole.setId(JavaConstants.ADMIN_USER);
		adminRole.setRole_name("ROLE_ADMIN");
		
		Role normalRole=new Role();
		normalRole.setId(JavaConstants.NORMAL_USER);
		normalRole.setRole_name("ROLE_NORMAL");
		
		rolesRepo.save(adminRole);
		rolesRepo.save(normalRole);
		
	}

}
