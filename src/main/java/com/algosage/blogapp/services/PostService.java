package com.algosage.blogapp.services;

import java.util.List;

import com.algosage.blogapp.payloads.PostDto;
import com.algosage.blogapp.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto newPostDto, int userId, int categoryId);
	
	//update
	PostDto updatePost(PostDto updatePostDto, int postId);
	
	//delete
	void deletePost(int postId);
	
	//get
	PostDto getPostById(int postId);
	
	//get all
	PostResponse getAllPost(int pageNumber, String sortBy, String sortDir);
	
	//get all post by category
	PostResponse getPostByCategory(int categoryId, int pageNumber, String sortBy);
	
	//get all post by user
	PostResponse getPostByUser(int userId, int pageNumber, String sortBy);
	
	//search post
	List<PostDto> searchPosts(String keyword);
	
}
