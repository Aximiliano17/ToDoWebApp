package com.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.todo.domain.Task;
import com.todo.repository.TaskRepository;

public class TaskService {
	@Autowired
	private TaskRepository taskRepo;

	public List<Task> getAllTasks() {
		List<Task> tasks = new ArrayList<>();
		taskRepo.findAll().forEach(tasks::add);
		return tasks;
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

	public Task getTask(Integer id) {
		return taskRepo.findById(id).get();
	}
	
}
