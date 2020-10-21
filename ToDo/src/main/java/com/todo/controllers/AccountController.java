package com.todo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.todo.domain.User;

@Controller
public class AccountController {

	@GetMapping("/ChangeUsername")
	public String getUsername(@AuthenticationPrincipal User user, Model model) {
System.out.println("Testing");
		return "changeUsername.html";
	}
	@GetMapping("/ChangePassword")
	public String getPasword(@AuthenticationPrincipal User user, Model model) {

		return "changePassword.html";
	}
}
