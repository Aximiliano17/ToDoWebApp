package com.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.domain.Project;
import com.todo.domain.User;
import com.todo.repository.ProjectRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepo;

	public Page<Project> findAll()
	{
		Pageable pageable=PageRequest.of(0,10);
		return projectRepo.findAll(pageable);
	}
	
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

	public List<Project> getUserProjects(User user) {
		return projectRepo.findAllByUser(user);
	}

	public List<Project> findByCommentContainsOrNameContainsAllIgnoreCaseOrderByNameAsc(String comment, String name) {
		return findByCommentContainsOrNameContainsAllIgnoreCaseOrderByNameAsc(comment, name);
	}
}
