package com.algosage.blogapp.payloads;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;

	@NotEmpty
	private String name;

	@Email(message = "email is not valid")
	private String email;

	@NotEmpty
	@Size(min = 3, message = "password must be min of 3 chars and max of 10 chars")
//	@JsonIgnore
	private String password;

	@NotEmpty
	private String about;

	private Set<RoleDto> roles = new HashSet<>();

}
