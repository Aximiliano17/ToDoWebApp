package com.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DashController {
	
@GetMapping("/dash")
public String getDash()
{
	return "dash.html";
}
	
}
