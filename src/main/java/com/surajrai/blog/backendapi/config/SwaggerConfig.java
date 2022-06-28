package com.surajrai.blog.backendapi.config;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	private static final String AUTH_HEADER="Authorization";
	
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).
				apiInfo(getApiInfo()).securityContexts(getSecurityContexts()).securitySchemes(getApiKey()).
				select().
				apis(RequestHandlerSelectors.any()).
				paths(PathSelectors.any()).build();
	}

	private List<SecurityScheme> getApiKey() {
		ApiKey apiKey=new ApiKey("JWT", AUTH_HEADER, "header");
		return Arrays.asList(apiKey);
	}

	private List<SecurityContext> getSecurityContexts() {
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
	}

	private List<SecurityReference> sf() {
		AuthorizationScope scope=new AuthorizationScope("global", "access everything!!");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
	}

	private ApiInfo getApiInfo() {
	ApiInfo apiInfo=new ApiInfo("Blogging App : Backend Course", 
			"This is developed by Suraj", "2.0", "Suraj's T&C", new Contact("Suraj", "skr.com", "itssuraj@duck.com"), "License 2.0",
			"License URL", Collections.EMPTY_LIST);
		return apiInfo;
	}
}
