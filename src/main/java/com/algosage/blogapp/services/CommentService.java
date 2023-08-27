package com.algosage.blogapp.services;

import com.algosage.blogapp.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto newCommentDto, int postId);
	
	void deleteComment(int commentid);
}
