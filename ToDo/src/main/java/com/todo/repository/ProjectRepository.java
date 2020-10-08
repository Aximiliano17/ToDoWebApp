package com.todo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.domain.Project;
import com.todo.domain.Project.Progress;
import com.todo.domain.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	Page<Project> findAllByUser(User user,Pageable pageable);

	Page<Project> findByUserAndProgressAndTrashFalseAndNameContains(User user,Project.Progress progress,String keyword, Pageable pageable);

	List<Project> findByUserAndTrashTrue(User user);

	List<Project> findByUserAndProgressAndTrashFalse(User user, Progress progress, Sort sort);
}
