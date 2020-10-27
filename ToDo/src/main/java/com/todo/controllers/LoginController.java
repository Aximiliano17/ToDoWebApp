package com.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/")
	public String getRoot() {
		return "login.html";
	}

	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("hideFooter", true);
		return "login.html";
	}

}
