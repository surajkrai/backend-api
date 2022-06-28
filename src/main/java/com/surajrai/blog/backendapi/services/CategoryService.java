package com.surajrai.blog.backendapi.services;

import java.util.List;

import com.surajrai.blog.backendapi.payloads.CategoryDto;


public interface CategoryService {

	//add
	CategoryDto addCategory(CategoryDto categoryDto);
	
	//get
	CategoryDto getCategoryById(Integer categoryId);
	
	//getAll
	List<CategoryDto> getAllCategories();
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//delete
	void deleteCategory(Integer categoryId);
	
}
