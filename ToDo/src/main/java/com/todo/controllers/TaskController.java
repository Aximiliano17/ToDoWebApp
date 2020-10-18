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
import com.todo.domain.Task.Priority;
import com.todo.domain.User;
import com.todo.domain.Project;
import com.todo.domain.Project.Progress;
import com.todo.service.ProjectService;
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
	@Autowired
	private ProjectService projectService;

	@GetMapping("/tasks")
	public String getTasks(@AuthenticationPrincipal User user, Model model,
			@RequestParam(value = "project", required = false) Project project) {

		return listByPage(user, model, 1, project, "name", "asc", Progress.Incomplete, "");
	}

	@GetMapping("/tasks/page/{pageNumber}")
	public String listByPage(@AuthenticationPrincipal User user, Model model,
			@PathVariable("pageNumber") int currentPage,
			@RequestParam(value = "project", required = false) Project project, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("progress") Progress progress, @Param("keyword") String keyword) {

		List<Project> projects = projectService.findByUserAndProgressAndTrashFalse(user, Progress.Incomplete, "name");

		Page<Task> page = taskService.findByUserAndProjectAndProgressAndTrashFalseAndNameContains(user, project,
				currentPage, sortField, sortDir, progress, keyword);

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
		model.addAttribute("project", project);
		model.addAttribute("projects", projects);
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		return "tasks.html";
	}

	@GetMapping("/tasks/createTask")
	public String createTask(Model model, HttpServletResponse response, @AuthenticationPrincipal User user,
			@RequestParam Project project) throws Exception {

		Task task = new Task();
		task.setUser(user);
		model.addAttribute("task", task);
		model.addAttribute("project", project);
		return "taskCreation.html";
	}

	@GetMapping("/tasks/{taskId}")
	public String getTask(@PathVariable Integer taskId, Model model, HttpServletResponse response) throws IOException {
		Optional<Task> taskOpt = taskService.getTask(taskId);

		if (taskOpt.isPresent()) {
			Task task = taskOpt.get();
			model.addAttribute("task", task);

			List<Project> projects = projectService.findByUserAndProgressAndTrashFalse(task.getUser(),
					Progress.Incomplete, "");
			model.addAttribute("projects", projects);
		} else {
			response.sendError(HttpStatus.NOT_FOUND.value(), "Task with id " + taskId + " was not found");
			return "task";
		}

		return "task";
	}

	@GetMapping("/task/delete")
	public String deleteProject(@AuthenticationPrincipal User user, Model model, @RequestParam("task") Task task) {
		task.setTrash(true);
		taskService.saveTask(task);
		return getTasks(user, model, task.getProject());
	}

	@GetMapping("/task/modify")
	public String modifyProject(@AuthenticationPrincipal User user, Model model, @RequestParam("task") Task task) {
		if (task.getProgress() == Progress.Completed) {
			task.setProgress(Progress.Incomplete);
		} else
			task.setProgress(Progress.Completed);

		taskService.saveTask(task);
		return getTasks(user, model, task.getProject());
	}

	@PostMapping("/tasks/createTask")
	public String saveTask(Model model, @ModelAttribute Task task, @RequestParam Project project) {
		System.out.println(project);
		taskService.addTask(task, project);
		return listByPage(task.getUser(), model, 1, project, "name", "asc", Progress.Incomplete, "");
	}

	@PostMapping("/tasks/{taskId}")
	public String modifyTask(@ModelAttribute Task task) {
		taskService.saveTask(task);
		return "redirect:/tasks";
	}
}
