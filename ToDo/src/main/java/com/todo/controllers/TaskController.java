package com.todo.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	@GetMapping("/tasks/{taskId}")
	public String getTask(@PathVariable Integer taskId, ModelMap model, HttpServletResponse response) throws IOException {
		Optional<Task> taskOpt = taskService.getTask(taskId);
	    
	    if (taskOpt.isPresent()) {
	      Task task = taskOpt.get();
	      model.put("task", task);
	    } else {
	      response.sendError(HttpStatus.NOT_FOUND.value(), "Task with id " + taskId + " was not found");
	      return "task";
	    }
		return "task";
	}

	@PostMapping("/tasks/createTask")
	public String saveTask(@ModelAttribute Task task) {
		taskService.addTask(task);
		return "redirect:/tasks";
	}
	@PostMapping("/tasks")
	public String createTask(@AuthenticationPrincipal User user) {
		return "redirect:/tasks/createTask";
	}
	@PostMapping("/tasks/{taskId}")
	public String modifyTask(@ModelAttribute Task task) {
		taskService.saveTask(task);
		return "redirect:/tasks";
	}
}
