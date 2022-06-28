package com.surajrai.blog.backendapi.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surajrai.blog.backendapi.entities.Category;
import com.surajrai.blog.backendapi.exceptions.ResourceNotFoundException;
import com.surajrai.blog.backendapi.payloads.CategoryDto;
import com.surajrai.blog.backendapi.repo.CategoryRepo;
import com.surajrai.blog.backendapi.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category=this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory,CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(
				()->new ResourceNotFoundException("Category", "categoryId", categoryId)
				);
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<CategoryDto> categories=this.categoryRepo.findAll().
				stream().map(c->this.modelMapper.map(c, CategoryDto.class)).collect(Collectors.toList());
		
		return categories;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category existingCategory = this.categoryRepo.findById(categoryId).orElseThrow(
				()->new ResourceNotFoundException("Category", "categoryId", categoryId)
				);
		existingCategory.setCategoryName(categoryDto.getCategoryName());
		existingCategory.setCategoryDesc(categoryDto.getCategoryDesc());
		Category savedCategory = this.categoryRepo.save(existingCategory);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(
				()->new ResourceNotFoundException("Category", "categoryId", categoryId)
				);
		this.categoryRepo.delete(category);
	}

}
