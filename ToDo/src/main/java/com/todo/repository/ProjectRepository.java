package com.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.todo.domain.Project;
import com.todo.domain.User;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {

	List<Project> findAllByUser(User user);

}
