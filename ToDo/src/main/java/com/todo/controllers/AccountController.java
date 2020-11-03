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
	public String getPassword(@AuthenticationPrincipal User user, Model model) {

		return "changePassword.html";
	}

	@PostMapping("/UpdateAccount")
	public String updateAccount(@AuthenticationPrincipal User user, Model model, @ModelAttribute Task task,
			@RequestParam(value = "oldUsername") String oldUsername, @RequestParam(value = "password") String password,
			@RequestParam(value = "newPassOrUser") String newPassOrUser,
			@RequestParam(value = "changePass") boolean changePass) {

		boolean correctPass = passwordEncoder.matches(password, user.getPassword());

		if (oldUsername.equals(user.getUsername()) && correctPass) {

			Optional<User> u = userService.getUser(user.getId());
			if (changePass) {
				u.get().setPassword(newPassOrUser);
			} else {
				u.get().setUsername(newPassOrUser);
			}
			userService.save(u.get(), changePass);
			return "redirect:/logout";
		}
		String message = "Incorrect Username or Password";
		return changePass ? getPassword(user, model) : getUsername(message, model);
	}
}
