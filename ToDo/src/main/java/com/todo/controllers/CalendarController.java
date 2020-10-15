package com.todo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.todo.domain.User;

@Controller
public class CalendarController {
	
	@GetMapping("/calendar")
	public String getCalendar(@AuthenticationPrincipal User user, Model model) {

		return "calendar.html";
	}

}
