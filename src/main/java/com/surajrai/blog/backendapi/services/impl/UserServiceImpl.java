package com.surajrai.blog.backendapi.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.surajrai.blog.backendapi.config.JavaConstants;
import com.surajrai.blog.backendapi.entities.Role;
import com.surajrai.blog.backendapi.entities.User;
import com.surajrai.blog.backendapi.exceptions.ResourceNotFoundException;
import com.surajrai.blog.backendapi.payloads.UserDto;
import com.surajrai.blog.backendapi.repo.RolesRepo;
import com.surajrai.blog.backendapi.repo.UserRepo;
import com.surajrai.blog.backendapi.services.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RolesRepo rolesRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user= dtoTOUser(userDto);
		User savedUser = userRepo.save(user);
		return userTOdto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id){
		User user=userRepo.findById(id).
				orElseThrow(()->new ResourceNotFoundException("User", "UserID", id));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		this.userRepo.save(user);
		return this.userTOdto(user);
	}

	@Override
	public UserDto getUserById(Integer id) {
		User user=userRepo.findById(id).
				orElseThrow(()->new ResourceNotFoundException("User", "UserID", id));
		return this.userTOdto(user);
	}

	@Override
	public List<UserDto> getUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userTOdto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer id)  {
		User user=userRepo.findById(id).
				orElseThrow(()->new ResourceNotFoundException("User", "UserID", id));
		this.userRepo.delete(user);

	}
	
	public User dtoTOUser(UserDto userDto) {
		//User user=new User(userDto.getId(), userDto.getName(), userDto.getEmail(), userDto.getPassword(), userDto.getAbout());
		User user=this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	public UserDto userTOdto(User user) {
		//UserDto userDto=new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getAbout());
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user= dtoTOUser(userDto);
		Role role = this.rolesRepo.findById(JavaConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		User savedUser = this.userRepo.save(user);
		return this.userTOdto(savedUser);
	}

}
