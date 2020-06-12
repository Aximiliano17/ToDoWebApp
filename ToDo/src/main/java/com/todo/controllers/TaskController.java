package com.todo.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.todo.domain.Task;
import com.todo.domain.Task.Difficulty;
import com.todo.domain.User;
import com.todo.service.TaskService;

/**
 * 
 * This is a Controller for both the tasks view and task view. One is for
 * displaying multiple tasks, another one is for creating and updating a task.
 *
 */
@Controller
public class TaskController {
	
	@Autowired
	private TaskService taskService;

	@GetMapping("/tasks")
	public String getTasks(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("tasks", taskService.getUserTasks(user));
		return "tasks.html";
	}

	@GetMapping("/tasks/createTask")
	public String getTask(ModelMap model, HttpServletResponse response, @AuthenticationPrincipal User user)
			throws Exception {

		Task task = new Task();
		task.setUser(user);
		model.put("task", task);
		model.put("difficulties", Difficulty.values());
		model.put("priorities", Difficulty.values());

		return "taskCreation.html";
	}

	@PostMapping("/tasks")
	public String createTask(@AuthenticationPrincipal User user) {
		return "redirect:/tasks/createTask";
	}

	@PostMapping("/tasks/createTask")
	public String saveTask(@ModelAttribute Task task) {
		taskService.addTask(task);
		return "redirect:/tasks";
	}
}
