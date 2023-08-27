package com.algosage.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algosage.blogapp.entities.Category;
import com.algosage.blogapp.exceptions.ResourceNotFoundException;
import com.algosage.blogapp.payloads.CategoryDto;
import com.algosage.blogapp.repositories.CategoryRepo;
import com.algosage.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto newCategoryDto) {
		Category cat = this.modelMapper.map(newCategoryDto, Category.class);
		Category addedcat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addedcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto updateCategoryDto, int categoryId) {

		updateCategoryDto.setCategoryId(categoryId);

		Category getCat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		getCat.setCategoryTitle(updateCategoryDto.getCategoryTitle());
		getCat.setCategoryDescription(updateCategoryDto.getCategoryDescription());
		Category updatedCat = this.categoryRepo.save(getCat);
		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> cats = this.categoryRepo.findAll();
		return cats.stream().map(e -> this.modelMapper.map(e, CategoryDto.class)).toList();
	}

	@Override
	public CategoryDto getCategoryById(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

}
