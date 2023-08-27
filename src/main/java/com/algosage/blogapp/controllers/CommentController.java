package com.algosage.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algosage.blogapp.payloads.ApiResponse;
import com.algosage.blogapp.payloads.CommentDto;
import com.algosage.blogapp.services.CommentService;

@RestController
@RequestMapping("/blog-api/v1/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto newCommentDto, @PathVariable int postId) {
		CommentDto commentDto = this.commentService.createComment(newCommentDto, postId);
		return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId) {
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
	}

}
