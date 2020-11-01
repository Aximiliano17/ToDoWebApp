package com.todo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.todo.domain.User;


@Controller
public class DashController {

	@GetMapping("/dash")
	public String getDash(@AuthenticationPrincipal User user, Model model) {
		String userName=user.getName();
		model.addAttribute("name", userName);
		return "dash.html";
	}
}
