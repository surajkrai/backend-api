package com.surajrai.blog.backendapi.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.surajrai.blog.backendapi.entities.Comment;
import com.surajrai.blog.backendapi.entities.Post;
import com.surajrai.blog.backendapi.exceptions.ResourceNotFoundException;
import com.surajrai.blog.backendapi.payloads.CommentDto;
import com.surajrai.blog.backendapi.repo.CommentRepo;
import com.surajrai.blog.backendapi.repo.PostRepo;
import com.surajrai.blog.backendapi.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	PostRepo postRepo;
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CommentRepo commentRepo;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(
				()->new ResourceNotFoundException("Post", "postId", postId)
				);
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(
				()->new ResourceNotFoundException("Comment", "commentId", commentId)
				);
		
		this.commentRepo.delete(comment);
	}

}
