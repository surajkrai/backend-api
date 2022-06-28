package com.surajrai.blog.backendapi.services;

import java.util.List;

import com.surajrai.blog.backendapi.payloads.UserDto;

public interface UserService {
	
	UserDto registerUser(UserDto user);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer id);
	UserDto getUserById(Integer id) ;
	List<UserDto> getUsers();
	void deleteUser(Integer id);
}
