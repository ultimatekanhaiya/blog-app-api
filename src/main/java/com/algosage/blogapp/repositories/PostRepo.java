package com.algosage.blogapp.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.algosage.blogapp.entities.Category;
import com.algosage.blogapp.entities.Post;
import com.algosage.blogapp.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	Page<Post> findByUser(User user,  Pageable pageable);
	Page<Post> findByCategory(Category category,  Pageable pageable);
	List<Post> findByTitleContaining(String title);
}
