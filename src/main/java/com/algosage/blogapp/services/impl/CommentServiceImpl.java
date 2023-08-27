package com.algosage.blogapp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algosage.blogapp.entities.Comment;
import com.algosage.blogapp.entities.Post;
import com.algosage.blogapp.exceptions.ResourceNotFoundException;
import com.algosage.blogapp.payloads.CommentDto;
import com.algosage.blogapp.repositories.CommentRepo;
import com.algosage.blogapp.repositories.PostRepo;
import com.algosage.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto newCommentDto, int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		Comment comment = this.modelMapper.map(newCommentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment save = this.commentRepo.save(comment);
		 
		return this.modelMapper.map(save, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Id", commentId));
		this.commentRepo.delete(comment);
	}

}
