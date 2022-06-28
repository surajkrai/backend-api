package com.surajrai.blog.backendapi.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date postDate;
	private CategoryDto category;
	private UserDto user;
	
	List<CommentDto> comments=new ArrayList<CommentDto>();
	
}
