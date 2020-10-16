package com.todo.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.domain.Project;
import com.todo.domain.Project.Progress;
import com.todo.domain.Task;
import com.todo.domain.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findAllByUser(User user);
	
	Page<Task> findByUserAndProjectAndProgressAndTrashFalseAndNameContains(User user,Project project,Project.Progress progress,String keyword, Pageable pageable);

	List<Task> findByUserAndProgressAndTrashFalse(User user, Progress progress);
}
