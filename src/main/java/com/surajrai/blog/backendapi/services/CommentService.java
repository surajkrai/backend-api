package com.surajrai.blog.backendapi.services;

import com.surajrai.blog.backendapi.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);
}
