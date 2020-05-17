package com.todo.service;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.not;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsServiceTest {

	@Test
	public void generate_encrypted_password() {
		BCryptPasswordEncoder passEncoder=new BCryptPasswordEncoder();
		String rawPass="123";
		String encodedPass=passEncoder.encode(rawPass);
		
		assertThat(rawPass,not(encodedPass));
		System.out.println(encodedPass);
	}
	

}
