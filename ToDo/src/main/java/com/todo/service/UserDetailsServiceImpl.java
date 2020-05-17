package com.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todo.domain.User;
import com.todo.repository.UserRepository;
import com.todo.security.CustomSecurityUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user=userRepo.findByUsername(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("Invalid User or Pass");
		}
		return new CustomSecurityUser(user);
	}

}
