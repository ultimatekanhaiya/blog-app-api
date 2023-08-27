package com.algosage.blogapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algosage.blogapp.payloads.ApiResponse;
import com.algosage.blogapp.payloads.CategoryDto;
import com.algosage.blogapp.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog-api/v1/categories")
@CrossOrigin("*")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto newCategoryDto) {
		CategoryDto catDto = this.categoryService.createCategory(newCategoryDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(catDto);
	}
	
	//update
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto updateCategoryDto, @PathVariable ("id") int categoryId) {
		CategoryDto catDto = this.categoryService.updateCategory(updateCategoryDto, categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(catDto);
	}	
	
	//delete
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable ("id") int categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse("category deleted successfully", true),HttpStatus.OK);
	}	
	
	//get one
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable ("id") int categoryId) {
		CategoryDto catDto = this.categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok(catDto);
	}	
	
	//get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> catDtos = this.categoryService.getAllCategory();
		return ResponseEntity.ok(catDtos);
	}
}
