package com.todo.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.todo.domain.User;
import com.todo.domain.Project.Progress;
import com.todo.domain.Task;
import com.todo.domain.Project;
import com.todo.service.ProjectService;
import com.todo.service.TaskService;

@Controller
public class CalendarController {
	@Autowired
	TaskService taskService;
	@Autowired
	ProjectService projectService;
	
	@GetMapping("/calendar")
	public String getCalendar(@AuthenticationPrincipal User user, Model model) {
        LocalDate date=LocalDate.now();
        model.addAttribute("date",date);
        List<Project> projects=projectService.findByUserAndProgressAndTrashFalse(user, Progress.Incomplete,"name");
        List<Task> tasks=taskService.findByUserAndProgressAndTrashFalse(user, Progress.Incomplete);
        model.addAttribute("projects",projects);
        model.addAttribute("tasks",tasks);
		return "calendar.html";
	}
}
