package com.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
@GetMapping(value="login")
public String getLogin()
{
	return "login.jsp";
}
}
