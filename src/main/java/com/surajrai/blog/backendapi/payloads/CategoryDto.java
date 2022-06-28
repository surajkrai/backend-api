package com.surajrai.blog.backendapi.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotEmpty(message = "Category Name can't be empty!!")
	@Size(min = 3, max = 10,message = "Category Name should be 3 to 10 characters!!")
	private String categoryName;
	
	private String categoryDesc;

}
