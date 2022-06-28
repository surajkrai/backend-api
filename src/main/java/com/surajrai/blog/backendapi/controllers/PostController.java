package com.surajrai.blog.backendapi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.surajrai.blog.backendapi.config.JavaConstants;
import com.surajrai.blog.backendapi.payloads.ApiResponse;
import com.surajrai.blog.backendapi.payloads.PostDto;
import com.surajrai.blog.backendapi.payloads.PostResponse;
import com.surajrai.blog.backendapi.services.FileService;
import com.surajrai.blog.backendapi.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Value("${project.imagepath}")
	private String path;
	
	@Autowired
	PostService postService;
	
	@Autowired
	FileService fileService;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts/")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId, @PathVariable Integer categoryId) {
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
	//Get
	
	@GetMapping("/user/{userId}/posts/")
	public ResponseEntity<List<PostDto>> getAllPostsByUser(@PathVariable Integer userId){
		List<PostDto> allPostsByUser = this.postService.getAllPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(allPostsByUser,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts/")
	public ResponseEntity<List<PostDto>> getAllPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> allPostsByCategory = this.postService.getAllPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(allPostsByCategory,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(name = "pageNumber",defaultValue = JavaConstants.DEFAULT_PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(name = "pageSize",defaultValue = JavaConstants.DEFAULT_PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(name = "sortBy",defaultValue = JavaConstants.DEFAULT_SORTING_FIELD,required = false) String sortBy,
			@RequestParam(name = "sortOrder",defaultValue = JavaConstants.DEFAULT_SORTING_ORDER,required = false) String sortOrder
			){
		PostResponse allPosts = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortOrder);
		return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getAllPosts(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		ApiResponse apiResponse=new ApiResponse("Post Deleted with Id: "+postId, true);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	
	//update
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId) {
		PostDto updatedPost = this.postService.upatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	

	//search
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword){ 
		List<PostDto> postDtos=this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}
	
	//upload image	
	
	@PostMapping("/post/imageupload/{postId}")
	public ResponseEntity<PostDto> uploadFile(@PathVariable Integer postId,@RequestParam MultipartFile image) {
		
		PostDto postDto = this.postService.getPostById(postId);
		String uploadedFile=null;
		PostDto updatedPost=null;
		try {
			uploadedFile = this.fileService.uploadFile(path, image);
			postDto.setImageName(uploadedFile);			
			updatedPost = this.postService.upatePost(postDto, postId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	//serve file
	@GetMapping(path = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void getFile(@PathVariable String imageName,HttpServletResponse response) throws IOException {
		InputStream file = this.fileService.getFile(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(file, response.getOutputStream());
		
	}
}
