package com.surajrai.blog.backendapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surajrai.blog.backendapi.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
