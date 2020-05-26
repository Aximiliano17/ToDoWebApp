package com.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.domain.Task;
import com.todo.domain.Task.Difficulty;
import com.todo.domain.Task.Priority;
import com.todo.domain.User;
import com.todo.repository.TaskRepository;

@Controller
public class TaskController {
	@Autowired
	private TaskRepository taskRepo;
	
	@GetMapping("/tasks")
	public String getTasks() {
		return "tasks.html";
	}

	@PostMapping("/tasks")
	public String createTask(@AuthenticationPrincipal User user) {
		Task task = new Task();
		task.setUser(user);
		task.setPriority(Priority.LOW);
		task.setDifficulty(Difficulty.MEDIUM);
		task=taskRepo.save(task);
		return "redirect:/tasks";
	}
}
