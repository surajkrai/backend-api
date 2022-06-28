package com.surajrai.blog.backendapi.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
	private String username,password;
	
}
