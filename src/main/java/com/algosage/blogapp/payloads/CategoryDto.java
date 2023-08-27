package com.algosage.blogapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private int categoryId;
	
	@NotEmpty
	private String categoryTitle;
	
	@Size(min = 3, message = "description must be > 3 char")
	private String categoryDescription;
	

}
