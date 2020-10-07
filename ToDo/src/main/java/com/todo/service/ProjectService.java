package com.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.domain.Project;
import com.todo.domain.Project.Progress;
import com.todo.domain.User;
import com.todo.repository.ProjectRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository projectRepo;

	public Page<Project> findByUserAndProgressAndNameContains(User user, int pageNumber, String sortField,
			String sortDir, Project.Progress progress, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
		return projectRepo.findByUserAndProgressAndTrashFalseAndNameContains(user, progress, keyword, pageable);
	}

	public List<Project> findByUserAndTrashTrue(User user) {
		return projectRepo.findByUserAndTrashTrue(user);
	}
	public List<Project> findByUserAndProgressAndTrashFalse(User user, Progress progress, String string) {
		Sort sort = Sort.by(string);
		sort = sort.ascending();
		return projectRepo.findByUserAndProgressAndTrashFalse(user,progress,sort);
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
}
