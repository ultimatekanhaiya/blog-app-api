package com.algosage.blogapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algosage.blogapp.payloads.ApiResponse;
import com.algosage.blogapp.payloads.UserDto;
import com.algosage.blogapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog-api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	// POST-create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto newUserDto) {
		UserDto userDto = this.userService.createUser(newUserDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
	}

	// PUT-update user
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto updateUserDto, @PathVariable("id") int userId) {
		UserDto userDto = this.userService.updateUser(updateUserDto, userId);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}

	// DELETE-delete user
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/admin/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") int userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<>(new ApiResponse("user deleted successfully", true), HttpStatus.OK);
	}

	// GET-get user
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser() {
		List<UserDto> userDtos = this.userService.getAllUser();
		return ResponseEntity.status(HttpStatus.OK).body(userDtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") int userId) {
		UserDto userDtos = this.userService.getUserById(userId);
		return ResponseEntity.ok(userDtos);
	}

}
