package com.algosage.blogapp.services.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.algosage.blogapp.config.AppConstants;
import com.algosage.blogapp.entities.Category;
import com.algosage.blogapp.entities.Post;
import com.algosage.blogapp.entities.User;
import com.algosage.blogapp.exceptions.ResourceNotFoundException;
import com.algosage.blogapp.payloads.PostDto;
import com.algosage.blogapp.payloads.PostResponse;
import com.algosage.blogapp.repositories.CategoryRepo;
import com.algosage.blogapp.repositories.PostRepo;
import com.algosage.blogapp.repositories.UserRepo;
import com.algosage.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	private static int pageSize = Integer.parseInt(AppConstants.PAGE_SIZE);

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto newPostDto, int userId, int categoryId) {

		// getting user by id
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		// getting category by id
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		Post post = this.modelMapper.map(newPostDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto updatePostDto, int postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		post.setTitle(updatePostDto.getTitle());
		post.setContent(updatePostDto.getContent());
		post.setImageName(updatePostDto.getImageName());

		Post updatePost = this.postRepo.save(post);
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void deletePost(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(int pageNumber, String sortBy, String sortDir) { 

		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map(e -> this.modelMapper.map(e, PostDto.class)).toList();

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalpages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostResponse getPostByCategory(int categoryId, int pageNumber, String sortBy) {

		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		Page<Post> pagePost = this.postRepo.findByCategory(category, p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map(e -> this.modelMapper.map(e, PostDto.class)).toList();

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalpages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostResponse getPostByUser(int userId, int pageNumber, String sortBy) {

		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		Page<Post> pagePost = this.postRepo.findByUser(user, p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map(e -> this.modelMapper.map(e, PostDto.class)).toList();

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalpages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;

	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		return  posts.stream().map(e -> this.modelMapper.map(e, PostDto.class)).toList();
	}

}
