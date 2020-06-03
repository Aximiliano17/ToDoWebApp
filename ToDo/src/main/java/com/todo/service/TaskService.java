package com.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.domain.Task;
import com.todo.domain.User;
import com.todo.repository.TaskRepository;

import javassist.NotFoundException;

@Service
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

	public Optional<Task> getTask(Integer id) {
		Optional<Task> taskOpt = taskRepo.findById(id);
			return taskOpt;

}

	public Object getUserTasks(User user) {
		return taskRepo.findAllByUser(user);
	}
}
