package com.todo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.domain.Task;
import com.todo.domain.Task.Difficulty;
import com.todo.domain.Task.Priority;
import com.todo.domain.User;
import com.todo.domain.Project;
import com.todo.domain.Project.Progress;
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
		String keyword = "";
		Progress progress = Progress.Incomplete;
		Project project=null;
		return listByPage(user,project, model, 1, "name", "asc", progress, keyword);
	}

	@GetMapping("/tasks/page/{pageNumber}")
<<<<<<< master
	public String listByPage(@AuthenticationPrincipal User user, Model model,
			@PathVariable("pageNumber") int currentPage, @RequestParam("project") Project project,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("progress") Progress progress, @Param("keyword") String keyword) {
		
		List<Project> projects = projectService.findByUserAndProgressAndTrashFalse(user, progress, "name");
		
		Page<Task> page = taskService.findByUserAndProjectAndProgressAndNameContains(user, project, currentPage,
				sortField, sortDir, progress, keyword);
		
=======
	public String listByPage(@AuthenticationPrincipal User user,Project project, Model model,
			@PathVariable("pageNumber") int currentPage, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("progress") Progress progress, @Param("keyword") String keyword) {
		Page<Task> page = taskService.findByUserAndProjectAndProgressAndNameContains(user,project, currentPage, sortField, sortDir,
				progress, keyword);
>>>>>>> 65e86f4 Implement select tasks by project
		List<Task> tasks = page.getContent();
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		model.addAttribute("tasks", tasks);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("progress", progress);
		model.addAttribute("keyword", keyword);
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		return "tasks.html";
	}

	@GetMapping("/tasks/createTask")
	public String createTask(Model model, HttpServletResponse response, @AuthenticationPrincipal User user, @RequestParam Project project)
			throws Exception {

		Task task = new Task();
		task.setUser(user);
		model.addAttribute("task", task);
		model.addAttribute("difficulties", Difficulty.values());
		model.addAttribute("priorities", Priority.values());
		model.addAttribute("project", project);
		System.out.println(project);
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
	public String saveTask(@ModelAttribute Task task, @RequestParam Project project) {
		System.out.println(project);
		taskService.addTask(task, project);
		return "redirect:/tasks";
	}
<<<<<<< master

=======
	@PostMapping("/tasks")
	public String createTask(@AuthenticationPrincipal User user) {
		return "redirect:/tasks/createTask";
	}
>>>>>>> 65e86f4 Implement select tasks by project
	@PostMapping("/tasks/{taskId}")
	public String modifyTask(@ModelAttribute Task task) {
		taskService.saveTask(task);
		return "redirect:/tasks";
	}
}
