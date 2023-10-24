package com.coderkan.services;

import java.util.List;

import com.coderkan.models.User;

public interface UserService {

	public List<User> getAll();

	public User add(User customer);

	public User update(User customer);

	public void delete(long id);

	public User getUserById(long id);
}
