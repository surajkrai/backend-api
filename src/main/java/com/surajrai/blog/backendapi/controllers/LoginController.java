package com.surajrai.blog.backendapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surajrai.blog.backendapi.exceptions.ApiException;
import com.surajrai.blog.backendapi.payloads.JwtAuthRequest;
import com.surajrai.blog.backendapi.payloads.JwtAuthResponse;
import com.surajrai.blog.backendapi.payloads.UserDto;
import com.surajrai.blog.backendapi.security.JWTHelper;
import com.surajrai.blog.backendapi.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JWTHelper jwtHelper;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> generateToken(@RequestBody JwtAuthRequest request){
		UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getUsername());
		this.validate(request);
		String token = this.jwtHelper.generateToken(userDetails);
		
		JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
		
	}

	private void validate(JwtAuthRequest request)  {
		UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
		try {
			this.authenticationManager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new ApiException("Password isn't correct!!");

		}
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registeredUser = this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
}
