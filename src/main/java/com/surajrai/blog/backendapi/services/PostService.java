package com.surajrai.blog.backendapi.services;

import java.util.List;

import com.surajrai.blog.backendapi.payloads.PostDto;
import com.surajrai.blog.backendapi.payloads.PostResponse;

public interface PostService {

	//create post
	PostDto createPost(PostDto postDto, Integer userId,Integer categoryId);
	
	
	//get post
	List<PostDto> getAllPostsByUser(Integer userId);
	List<PostDto> getAllPostsByCategory(Integer categoryId);
	
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	PostDto getPostById(Integer postId);
	
	//delete post
	void deletePost(Integer postId);
	
	//updatePost
	PostDto upatePost(PostDto postDto, Integer postId);
	
	//search
	List<PostDto> searchPost(String keyword);
	
	
}
