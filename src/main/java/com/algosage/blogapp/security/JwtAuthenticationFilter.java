package com.algosage.blogapp.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//1. get token
		String requestToken = request.getHeader("Authorization");
		
		//Bearer 23232345sgfdg
		 logger.info(" Header :  {}", requestToken);
		System.out.println(requestToken);
		
		String username = null;		
		String token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch(IllegalArgumentException e) {
				System.out.println("Unable to get Jwt token");
			} catch(ExpiredJwtException e) {
				System.out.println("Jwt token has expired");
			} catch(MalformedJwtException e) {
				System.out.println("Invalid Jwt");
			}catch (Exception e) {
                e.printStackTrace();
            }
		} else {
			System.out.println("Jwt token does not start with Bearer");
		}
		
		//once we get the token, now validate
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				//working fine
				//need to authenticate
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,  userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			} else {
				System.out.println("Invalid Jwt token");
			}
		} else {
			System.out.println(" username is null or context is not null");
		}
		
		filterChain.doFilter(request, response);
	}

}
