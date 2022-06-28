package com.surajrai.blog.backendapi.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.surajrai.blog.backendapi.entities.User;
import com.surajrai.blog.backendapi.exceptions.ResourceNotFoundException;
import com.surajrai.blog.backendapi.repo.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username).orElseThrow(
				()->new ResourceNotFoundException("User", "email "+username, 0)
				);
		
		return user;
	}

}
