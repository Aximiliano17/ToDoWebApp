package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

}
