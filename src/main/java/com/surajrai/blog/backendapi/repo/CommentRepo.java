package com.surajrai.blog.backendapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.surajrai.blog.backendapi.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
