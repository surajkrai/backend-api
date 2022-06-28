package com.surajrai.blog.backendapi.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.surajrai.blog.backendapi.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExHandler(ResourceNotFoundException ex){
		String msg=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(msg, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleArgsNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> err=new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach(e->{
			String fieldName = ((FieldError) e).getField();
			String message = e.getDefaultMessage();
			err.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String,String>>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex){
		String msg=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(msg, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
		
	}
}