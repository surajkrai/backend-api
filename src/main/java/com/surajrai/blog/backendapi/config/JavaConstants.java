package com.surajrai.blog.backendapi.config;

public class JavaConstants {
	public static final String DEFAULT_SORTING_ORDER = "asc";
	public static final String DEFAULT_SORTING_FIELD = "postId";
	public static final String DEFAULT_PAGE_SIZE = "10";
	public static final String DEFAULT_PAGE_NUMBER = "0";
	
	public static final Integer ADMIN_USER=501;
	public static final Integer NORMAL_USER=502;
	
	public static final String[] PUBLIC_URLS= {
			"/api/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	
}
