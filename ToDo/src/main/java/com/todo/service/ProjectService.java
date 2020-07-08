package com.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.domain.Project;
import com.todo.domain.User;
import com.todo.repository.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepo;

	public List<Project> getAllprojects() {
		List<Project> projects = new ArrayList<>();
		projectRepo.findAll().forEach(projects::add);
		return projects;
	}

	public void addProject(Project project) {
		projectRepo.save(project);
	}

	public void saveProject(Project project) {
		projectRepo.save(project);
	}

	public void deleteProject(Project project) {
		projectRepo.delete(project);

	}

	public Optional<Project> getProject(Integer id) {
		Optional<Project> projectOpt = projectRepo.findById(id);
			return projectOpt;

}

	public Object getUserProjects(User user) {
		return projectRepo.findAllByUser(user);
	}
}
