package com.todo.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import com.todo.domain.Project;
import com.todo.domain.User;
import com.todo.domain.Project.Progress;
import com.todo.service.ProjectService;

/**
 * 
 * This is a Controller for both the projects and project view. One is for
 * displaying multiple projects, another one is for creating and updating a
 * project.
 *
 */
@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping("/projects")
	public String getProjects(@AuthenticationPrincipal User user, Model model) {
	
		return listByPage(user, model, 1, "name", "asc", Progress.Incomplete, "");
	}

	@GetMapping("/projects/page/{pageNumber}")
	public String listByPage(@AuthenticationPrincipal User user, Model model,
			@PathVariable("pageNumber") int currentPage, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("progress") Progress progress, @Param("keyword") String keyword) {
		Page<Project> page = projectService.findByUserAndProgressAndNameContains(user, currentPage, sortField, sortDir,
				progress, keyword);
		List<Project> projects = page.getContent();
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		model.addAttribute("projects", projects);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("progress", progress);
		model.addAttribute("keyword", keyword);
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		return "projects.html";
	}

	@GetMapping("/projects/createProject")
	public String getProject() {
		return "projectCreation.html";
	}

	@GetMapping("/projects/{project}")
	public String getProject(@PathVariable Project project, Model model, HttpServletResponse response)
			throws IOException {
			model.addAttribute("project", project);
			model.addAttribute("progress", project.getProgress());
		return "project";
	}
	
	@GetMapping("/project/delete")
	public String deleteProject(@AuthenticationPrincipal User user, Model model, @RequestParam("project") Project project )
	{
		project.setTrash(true);
		projectService.saveProject(project);
		return getProjects(user, model);
	}
	@GetMapping("/project/modify")
	public String modifyProject(@AuthenticationPrincipal User user, Model model, @RequestParam("project") Project project )
	{
		if(project.getProgress()==Progress.Completed)
		{
			project.setProgress(Progress.Incomplete);
		}
		else
			project.setProgress(Progress.Completed);
		
		projectService.saveProject(project);
		return getProjects(user, model);
	}

	@PostMapping("/projects/createProject")
	public String saveProject(@RequestParam String name, @RequestParam String description,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
			@AuthenticationPrincipal User user) {
		Project p = new Project();
		p.setUser(user);
		p.setName(name);
		p.setDescription(description);
		p.setDueDate(dueDate);
		projectService.addProject(p);
		return "redirect:/projects";
	}

	@PostMapping(value = { "/projects", "/projects/page/{pageNumber}" })
	public String createProject(@AuthenticationPrincipal User user) {
		return "redirect:/projects/createProject";
	}
	@PostMapping("/projects/{projectId}")
	public String modifyProject(@ModelAttribute Project project) {
		projectService.saveProject(project);
		return "redirect:/projects";
	}
	
	@PostMapping("/projects/delete")
	public String deleteProject(@RequestParam Integer projectId) {
		Project p=projectService.getProject(projectId).get();
		p.setTrash(true);
		projectService.saveProject(p);
		return "redirect:/projects";
	}
}