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

import com.todo.domain.Project;
import com.todo.domain.User;
import com.todo.service.ProjectService;

/**project
 * 
 * This is a Controller for both the projects view and  view. One is for
 * displaying multiple tasks, another one is for creating and updating a task.
 *
 */
@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;

	@GetMapping("/projects")
	public String getprojects(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("projects", projectService.getUserProjects(user));
		return "projects.html";
	}

	@GetMapping("/projects/createProject")
	public String getProject(ModelMap model, HttpServletResponse response, @AuthenticationPrincipal User user)
			throws Exception {

		Project project= new Project();
		project.setUser(user);
		model.put("project", project);
		return "projectCreation.html";
	}
	@GetMapping("/projects/{projectId}")
	public String getProject(@PathVariable Integer projectId, ModelMap model, HttpServletResponse response) throws IOException {
		Optional<Project> projectOpt = projectService.getProject(projectId);
	    
	    if (projectOpt.isPresent()) {
	      Project project = projectOpt.get();
	      model.put("project", project);
	    } else {
	      response.sendError(HttpStatus.NOT_FOUND.value(), "Project with id " + projectId + " was not found");
	      return "project";
	    }
		return "project";
	}

	@PostMapping("/projects/createProject")
	public String saveProject(@ModelAttribute Project project) {
		projectService.addProject(project);
		return "redirect:/projects";
	}
	@PostMapping("/projects")
	public String createProject(@AuthenticationPrincipal User user) {
		return "redirect:/projects/createProject";
	}
	@PostMapping("/projects/{projectId}")
	public String modifyProject(@ModelAttribute Project project) {
		projectService.saveProject(project);
		return "redirect:/projects";
	}
}