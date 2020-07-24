package com.todo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.todo.domain.Project;
import com.todo.domain.User;
import com.todo.service.ProjectService;

/**
 * project
 * 
 * This is a Controller for both the projects view and view. One is for
 * displaying multiple tasks, another one is for creating and updating a task.
 *
 */
@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}

	@GetMapping("/projects")
	 public ResponseEntity<List<Project>> getAllProjects(@RequestParam(defaultValue = "name,desc") String[] sort) {

	    try {
	      List<Order> orders = new ArrayList<Order>();

	      if (sort[0].contains(",")) {
	        // will sort more than 2 columns
	        for (String sortOrder : sort) {
	          // sortOrder="column, direction"
	          String[] _sort = sortOrder.split(",");
	          orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
	        }
	      } else {
	        // sort=[column, direction]
	        orders.add(new Order(getSortDirection(sort[1]), sort[0]));
	      }

	      List<Project> projects = projectService.findAll(Sort.by(orders));

	      if (projects.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(projects, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }

	@GetMapping("/projects/createProject")
	public String getProject(ModelMap model, HttpServletResponse response, @AuthenticationPrincipal User user)
			throws Exception {

		Project project = new Project();
		project.setUser(user);
		model.put("project", project);
		return "projectCreation.html";
	}

	@GetMapping("/projects/{projectId}")
	public String getProject(@PathVariable Integer projectId, ModelMap model, HttpServletResponse response)
			throws IOException {
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