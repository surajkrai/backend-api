package com.surajrai.blog.backendapi.exceptions;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {

	private String resourceName,fieldName;
	private long fieldValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, int fieldValue) {
		super(String.format("%s not found with %s : %s ", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public ResourceNotFoundException() {
		super();
	}

	

	
}
