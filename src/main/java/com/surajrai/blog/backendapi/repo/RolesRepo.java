package com.surajrai.blog.backendapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surajrai.blog.backendapi.entities.Role;

public interface RolesRepo extends JpaRepository<Role, Integer> {

}
