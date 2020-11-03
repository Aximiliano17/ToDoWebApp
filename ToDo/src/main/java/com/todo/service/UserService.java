package com.todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.domain.Task;
import com.todo.domain.User;
import com.todo.repository.UserRepository;
import com.todo.security.Authority;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User save(User user, boolean encodePass) {
		if(encodePass==true)
		{
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		}
		if(user.getAuthorities().isEmpty())
		{
		Authority authority = new Authority();
		authority.setAuthority("ROLE_USER");
		authority.setUser(user);
		user.getAuthorities().add(authority);
		}
		return userRepo.save(user);
	}

	public Optional<User> getUser(Integer id) {
		Optional<User> userOpt = userRepo.findById(id);
		return userOpt;
	}
	
}
