package com.todo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.domain.User;


@Controller
public class DashController {

	@GetMapping("/dash")
	public String getDash() {
		return "dash.html";
	}
	@PostMapping("/dash")
	public String createTask(@AuthenticationPrincipal User user) {
		return "redirect:/tasks";
	}
}
