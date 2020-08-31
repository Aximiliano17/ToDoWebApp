package com.todo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.todo.domain.Project;
import com.todo.domain.User;
import com.todo.service.ProjectService;

@Controller
public class TrashController {

	@Autowired
	private ProjectService projectService;

	@GetMapping("/trash")
	public String getTrash(@AuthenticationPrincipal User user, Model model) {
		List<Project> projects= projectService.findByUserAndTrashTrue(user);
		model.addAttribute("projects", projects);
		//model.addAttribute("keyword",keyword);
		System.out.println("Happening");
		return "trash.html";
	}
}
