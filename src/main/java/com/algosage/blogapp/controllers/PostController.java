package com.algosage.blogapp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algosage.blogapp.config.AppConstants;
import com.algosage.blogapp.payloads.ApiResponse;
import com.algosage.blogapp.payloads.PostDto;
import com.algosage.blogapp.payloads.PostResponse;
import com.algosage.blogapp.services.FileService;
import com.algosage.blogapp.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/blog-api/v1")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto newPostDto, @PathVariable int userId,
			@PathVariable int categoryId) {
		PostDto postDto = this.postService.createPost(newPostDto, userId, categoryId);
		return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@PathVariable int userId) {
		PostResponse allPost = this.postService.getPostByUser(userId, pageNumber, sortBy);
		return ResponseEntity.status(HttpStatus.OK).body(allPost);
	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@PathVariable int categoryId) {
		PostResponse allPost = this.postService.getPostByCategory(categoryId, pageNumber, sortBy);
		return ResponseEntity.status(HttpStatus.OK).body(allPost);
	}

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse allPost = this.postService.getAllPost(pageNumber, sortBy, sortDir);
		return ResponseEntity.ok(allPost);
	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId) {
		PostDto postDto = this.postService.getPostById(postId);
		return ResponseEntity.ok(postDto);
	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto updatePostDto, @PathVariable int postId) {
		PostDto postDto = this.postService.updatePost(updatePostDto, postId);
		return ResponseEntity.ok(postDto);
	}

	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword) {
		List<PostDto> postDtos = this.postService.searchPosts(keyword);
		return ResponseEntity.ok(postDtos);
	}

	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam(value = "image") MultipartFile image,
			@PathVariable("postId") int postId) throws IOException {

		PostDto postDto = this.postService.getPostById(postId);

		String fileName = this.fileService.uploadImage(path, image);

		postDto.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatedPost);
	}

	// method to serve files
	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
	public void downloadIamge(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
