package com.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/")
	public String getRoot() {
		return "index";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login.html";
	}

}
