package com.surajrai.blog.backendapi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surajrai.blog.backendapi.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
