package com.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class DashController {

	@GetMapping("/dash")
	public String getDash() {
		return "dash.html";
	}
}
