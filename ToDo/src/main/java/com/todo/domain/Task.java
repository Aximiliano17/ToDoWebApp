package com.todo.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Task {
	
public enum Priority{
	LOW,MEDIUM,HIGH
}
public enum Difficulty
{
	EASY,MEDIUM,HARD
}

private Integer id;
private String name;
private String description;
private boolean isTrash;
private Priority priority;
private Difficulty difficulty;
private User user;
private Project.Progress progress;
private Project project;

public Task()
{
	progress=Project.Progress.Incomplete;
}

//Getters and Setters
@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
public Integer getId() {
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
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
@ManyToOne
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public Priority getPriority() {
	return priority;
}
public void setPriority(Priority priority) {
	this.priority = priority;
}
public Difficulty getDifficulty() {
	return difficulty;
}
public void setDifficulty(Difficulty difficulty) {
	this.difficulty = difficulty;
}
public Project.Progress getProgress() {
	return progress;
}
public void setProgress(Project.Progress progress) {
	this.progress = progress;
}
@ManyToOne
public Project getProject() {
	return project;
}
public void setProject(Project project) {
	this.project = project;
}

public boolean isTrash() {
	return isTrash;
}

public void setTrash(boolean isTrash) {
	this.isTrash = isTrash;
}

}
