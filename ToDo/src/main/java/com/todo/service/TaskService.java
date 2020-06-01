package com.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.todo.domain.Task;
import com.todo.repository.TaskRepository;

import javassist.NotFoundException;

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

	public Task getTask(Integer id) throws Exception {
		Optional<Task> taskOpt = taskRepo.findById(id);
		if (taskOpt.isPresent()) {
			return taskOpt.get();
		} else
			throw new NotFoundException("Task not found");
	}
}
