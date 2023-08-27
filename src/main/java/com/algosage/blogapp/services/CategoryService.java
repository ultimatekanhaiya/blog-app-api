package com.algosage.blogapp.services;

import java.util.List;

import com.algosage.blogapp.payloads.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto newCategoryDto);
	
	CategoryDto updateCategory(CategoryDto updateCategoryDto, int categoryId);
	
	void deleteCategory(int categoryId);
	
	List<CategoryDto> getAllCategory();
	
	CategoryDto getCategoryById(int categoryId);
}
