package com.todo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.domain.Project;
import com.todo.domain.Task;
import com.todo.domain.User;
import com.todo.service.ProjectService;
import com.todo.service.TaskService;

@Controller
public class TrashController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private TaskService taskService;

	@GetMapping("/trash")
	public String getTrash(@AuthenticationPrincipal User user, Model model,@RequestParam(required=false) String type) {
		
		if(type==null)
			type="projects";
		
		return getTrashType(user, model, 1, type, "");
	}

	@GetMapping("/trash/page/{pageNumber}")
	public String getTrashType(@AuthenticationPrincipal User user, Model model,
			@PathVariable("pageNumber") int currentPage, @RequestParam String type, @Param("keyword") String keyword) {

		long totalItems = 0;
		int totalPages = 0;
		if (type.equals("projects")) {
			Page<Project> page = projectService.findByUserAndTrashTrueAndNameContains(user, "name", keyword,
					currentPage);
			List<Project> projects = page.getContent();
			totalItems = page.getTotalElements();
			totalPages = page.getTotalPages();
			model.addAttribute("list", projects);
		} else if (type.equals("tasks")) {
			Page<Task> page = taskService.findByUserAndTrashTrueAndNameContains(user, "name", keyword, currentPage);
			List<Task> tasks = page.getContent();
			totalItems = page.getTotalElements();
			totalPages = page.getTotalPages();
			model.addAttribute("list", tasks);
		} else
			System.out.println("Error");

		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", type);

		return "trash.html";
	}
	
	@GetMapping("/trash/delete")
	public String delete(@AuthenticationPrincipal User user, Model model, @RequestParam(value="id") Integer id, @RequestParam String type) {
		
		if(type.equals("projects"))
		{
			Project project=projectService.getProject(id).get();
			projectService.deleteProject(project);
		}
		else if(type.equals("tasks"))
		{
			Task task=taskService.getTask(id).get();
			taskService.deleteTask(task);
		}
		return getTrash(user, model,type);
	}

}
