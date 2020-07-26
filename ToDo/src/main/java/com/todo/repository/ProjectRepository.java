package com.todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.todo.domain.Project;
import com.todo.domain.User;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {

	Page<Project> findAllByUser(User user,Pageable pageable);

}
