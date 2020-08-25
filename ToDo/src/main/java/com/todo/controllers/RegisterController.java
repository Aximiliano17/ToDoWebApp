package com.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.domain.User;
import com.todo.service.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String registerGet(ModelMap model) {
		model.put("user", new User());
		model.addAttribute("hideFooter", true);
		return "register.html";
	}

	@PostMapping("/register")
	public String registerPost(User user) {
		User savedUser = userService.save(user);
		return "redirect:/login";
	}
}
