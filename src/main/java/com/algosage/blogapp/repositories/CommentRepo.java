package com.algosage.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algosage.blogapp.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
