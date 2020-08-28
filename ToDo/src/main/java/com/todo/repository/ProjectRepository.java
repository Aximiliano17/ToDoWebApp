package com.todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.domain.Project;
import com.todo.domain.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	Page<Project> findAllByUser(User user,Pageable pageable);

	Page<Project> findByUserAndNameContains(User user,String keyword, Pageable pageable);
	

}
