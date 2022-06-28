package com.surajrai.blog.backendapi.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.surajrai.blog.backendapi.entities.Category;
import com.surajrai.blog.backendapi.entities.Post;
import com.surajrai.blog.backendapi.entities.User;
import com.surajrai.blog.backendapi.exceptions.ResourceNotFoundException;
import com.surajrai.blog.backendapi.payloads.PostDto;
import com.surajrai.blog.backendapi.payloads.PostResponse;
import com.surajrai.blog.backendapi.repo.CategoryRepo;
import com.surajrai.blog.backendapi.repo.PostRepo;
import com.surajrai.blog.backendapi.repo.UserRepo;
import com.surajrai.blog.backendapi.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepo postRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(
				()->new ResourceNotFoundException("User", "UserId", userId)
				);
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(
				()->new ResourceNotFoundException("Category", "CategoryId", categoryId)
				);
		
		Post post = this.modelMapper.map(postDto, Post.class);
		
		post.setUser(user);
		post.setCategory(category);
		post.setImageName("default.png");
		post.setPostDate(new Date());
		
		Post savedPost = this.postRepo.save(post);
		//System.out.println(post.getCategory());
		return this.modelMapper.map(savedPost, PostDto.class);
		
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(
				()->new ResourceNotFoundException("User", "UserId", userId)
				);
		 List<PostDto> posts = this.postRepo.findPostByUser(user).stream()
		 .map(u->this.modelMapper.map(u, PostDto.class)).collect(Collectors.toList());
		 
		return posts;
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(
				()->new ResourceNotFoundException("Category", "CategoryId", categoryId)
				);
		 List<PostDto> posts = this.postRepo.findPostByCategory(category).stream()
				 .map(u->this.modelMapper.map(u, PostDto.class)).collect(Collectors.toList());
		return posts;
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortOrder) {
		Sort sort=Sort.by(sortBy);
		sort=sortOrder.equalsIgnoreCase("asc")?sort.ascending():sort.descending();
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pages = this.postRepo.findAll(pageable);
		List<Post> posts = pages.getContent();
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setPosts(postDtos);
		postResponse.setPageNumber(pages.getNumber());
		postResponse.setPageSize(pages.getSize());
		postResponse.setTotalElements(pages.getTotalElements());
		postResponse.setTotalPages(pages.getTotalPages());
		postResponse.setLastPage(pages.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post", "postId", postId)
				);
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post", "postId", postId)
				);
		//System.out.println(post);
		this.postRepo.delete(post);
	}

	@Override
	public PostDto upatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post", "postId", postId)
				);
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setPostDate(new Date());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
