package com.coderkan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderkan.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

}

