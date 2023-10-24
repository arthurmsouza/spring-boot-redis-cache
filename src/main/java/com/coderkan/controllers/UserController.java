package com.coderkan.controllers;

import java.util.List;

import com.coderkan.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coderkan.services.UserService;


@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "/users")
	public ResponseEntity<Object> getAllUsers() {
		List<User> users = this.userService.getAll();
		return ResponseEntity.ok(users);
	}

	@GetMapping(value = "/users/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable("id") String id) {
		Long _id = Long.valueOf(id);
		User user = this.userService.getUserById(_id);
		return ResponseEntity.ok(user);
	}

	@PostMapping(value = "/users")
	public ResponseEntity<Object> addUser(@RequestBody User user) {
		User created = this.userService.add(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping(value = "/users")
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		User updated = this.userService.update(user);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<Object> deleteUserById(@PathVariable("id") String id) {
		Long _id = Long.valueOf(id);
		this.userService.delete(_id);
		return ResponseEntity.ok().build();
	}
}
