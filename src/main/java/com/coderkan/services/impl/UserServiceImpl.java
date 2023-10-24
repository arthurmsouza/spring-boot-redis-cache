package com.coderkan.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.coderkan.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.coderkan.repositories.UserRepository;
import com.coderkan.services.UserService;

@Service
@CacheConfig(cacheNames = "userCache")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	static final Logger LOGGER =
			Logger.getLogger(UserServiceImpl.class.getName());

	@Cacheable(cacheNames = "users")
	@Override
	public List<User> getAll() {
		//waitSomeTime();
		return this.userRepository.findAll();
	}

	@CacheEvict(cacheNames = "users", allEntries = true)
	@Override
	public User add(User user) {
		return this.userRepository.save(user);
	}

	@CacheEvict(cacheNames = "users", allEntries = true)
	@Override
	public User update(User user) {
		Optional<User> optUser = this.userRepository.findById(user.getId());
		if (!optUser.isPresent())
			return null;
		User repUser = optUser.get();
		repUser.setName(user.getName());
		repUser.setAddress(user.getAddress());
		repUser.setCity(user.getCity());
		repUser.setPostalCode(user.getPostalCode());
		repUser.setCountry(user.getCountry());
		return this.userRepository.save(repUser);
	}

	@Caching(evict = { @CacheEvict(cacheNames = "user", key = "#id"),
			@CacheEvict(cacheNames = "users", allEntries = true) })
	@Override
	public void delete(long id) {
		this.userRepository.deleteById(id);
	}

	@Cacheable(cacheNames = "user", key = "#id", unless = "#result == null")
	@Override
	public User getUserById(long id) {
		//waitSomeTime();
		return this.userRepository.findById(id).orElse(null);
	}

	private void waitSomeTime() {
		LOGGER.info("Long Wait Begin" );
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info("Long Wait End" );
	}

}