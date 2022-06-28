package com.surajrai.blog.backendapi.payloads;


import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.surajrai.blog.backendapi.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4, max = 10, message = "Name must be between 4 & 10 Characters!!")
	private String name;
	
	@Email(message = "Provide a valid email address!!")
	private String email;
	
	@NotEmpty(message = "Password can't be empty!")
	private String password;
	
	private String about;
	
	private Set<RoleDto> roles=new HashSet<RoleDto>();
}
