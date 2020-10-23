package com.todo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.domain.Task;
import com.todo.domain.User;
import com.todo.service.UserService;

@Controller
public class AccountController {
	@Autowired
	UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/ChangeUsername")
	public String getUsername(@RequestParam(value = "message", required = false) String message, Model model) {
		model.addAttribute("message", message);
		return "changeUsername.html";
	}

	@GetMapping("/ChangePassword")
	public String getPasword(@AuthenticationPrincipal User user, Model model) {

		return "changePassword.html";
	}

	@PostMapping("/ChangeUsername")
	public String saveUsername(@AuthenticationPrincipal User user, Model model, @ModelAttribute Task task,
			@RequestParam(value = "oldUsername") String oldUsername, @RequestParam(value = "password") String password,
			@RequestParam(value = "newUsername") String newUsername) {

		boolean correctPass = passwordEncoder.matches(password, user.getPassword());

		if (oldUsername.equals(user.getUsername()) && correctPass) {
			Optional<User> u = userService.getUser(user.getId());
			u.get().setUsername(newUsername);
			userService.save(u.get());
			return "redirect:/logout";
		}
		String message = "Incorrect Username or Password";
		return getUsername(message, model);
	}

	@PostMapping("/ChangePassword")
	public String savePassword(@AuthenticationPrincipal User user, Model model, @ModelAttribute Task task,
			@RequestParam(value = "oldUsername") String oldUsername, @RequestParam(value = "password") String password,
			@RequestParam(value = "newPassword") String newPassword) {

		boolean correctPass = passwordEncoder.matches(password, user.getPassword());

		if (oldUsername.equals(user.getUsername()) && correctPass) {
			Optional<User> u = userService.getUser(user.getId());
			String encodedPassword = passwordEncoder.encode(newPassword);
			u.get().setPassword(encodedPassword);
			userService.save(u.get());
			return "redirect:/logout";
		}
		String message = "Incorrect Username or Password";
		return getUsername(message, model);
	}
}
