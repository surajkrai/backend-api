package com.surajrai.blog.backendapi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surajrai.blog.backendapi.entities.Category;
import com.surajrai.blog.backendapi.entities.Post;
import com.surajrai.blog.backendapi.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	public List<Post> findPostByUser(User user);
	public List<Post> findPostByCategory(Category category);
	
	public List<Post> findByTitleContaining(String title);
}
