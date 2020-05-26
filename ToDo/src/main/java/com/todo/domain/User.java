package com.todo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.todo.security.Authority;

@Entity(name="users")
public class User {
private int id;
private String name;
private String username;
private String password;
private Set<Authority> authorities= new HashSet<>();
private Set<Task> tasks=new HashSet<>();


//Getters and Setters
@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="user")
public Set<Authority> getAuthorities() {
  return authorities;
}
public void setAuthorities(Set<Authority> authorities) {
  this.authorities = authorities;
}
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="user")
public Set<Task> getTasks() {
	return tasks;
}
public void setTasks(Set<Task> tasks) {
	this.tasks = tasks;
}
}
