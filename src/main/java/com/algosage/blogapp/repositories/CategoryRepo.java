package com.algosage.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algosage.blogapp.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
