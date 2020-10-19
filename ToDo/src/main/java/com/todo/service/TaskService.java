package com.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.todo.domain.Project;
import com.todo.domain.Project.Progress;
import com.todo.domain.Task;
import com.todo.domain.User;
import com.todo.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepo;

	public Page<Task> findByUserAndProjectAndProgressAndTrashFalseAndNameContains(User user, Project project,
			int pageNumber, String sortField, String sortDir, Project.Progress progress, String keyword) {

		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
		return taskRepo.findByUserAndProjectAndProgressAndTrashFalseAndNameContains(user, project, progress, keyword, pageable);
	}
//this method return all Incomplete Tasks-used by the Calendar view.
	public List<Task> findByUserAndProgressAndTrashFalse(User user, Progress progress) {
		return taskRepo.findByUserAndProgressAndTrashFalse(user, progress);
	}

	public void addTask(Task task) {
		taskRepo.save(task);
	}

	public void saveTask(Task task) {
		taskRepo.save(task);
	}

	public void deleteTask(Task task) {
		taskRepo.delete(task);

	}

	public Optional<Task> getTask(Integer id) {
		Optional<Task> taskOpt = taskRepo.findById(id);
		return taskOpt;

	}

	public Object getUserTasks(User user) {
		return taskRepo.findAllByUser(user);
	}
}
