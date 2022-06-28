package com.surajrai.blog.backendapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JWTHelper jwtHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestToken = request.getHeader("Authorization");
		String username=null;
		String token=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			 token = requestToken.substring(7);
			 
			 try {
				 username=this.jwtHelper.getUsernameFromToken(token);
			 }catch(IllegalArgumentException e1) {
				System.out.println("Unable to get JWTToken"); 
			 }catch(ExpiredJwtException e2) {
				 System.out.println("TOken has expired");
			 }catch (MalformedJwtException e3) {
				System.out.println("Invalid token");
			}
			 
		}else {
			System.out.println("token doesn't start with Bearer");
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			if(this.jwtHelper.validateToken(token, userDetails)) {
				
				UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}else {
				System.out.println("Invalid jwt token");
			}
		}else {
			System.out.println("username null or auth is not null");
		}
		
		filterChain.doFilter(request, response);
		
	}

}
