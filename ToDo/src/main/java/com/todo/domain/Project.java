package com.todo.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.todo.domain.Task.Priority;

@Entity
public class Project {

public enum Progression
{
	UNSTARTED,INPROGRESS,FINISHED
}
private Integer id;
private String name;
private String comment;
private Priority priority;
private User user;
private Progression progress;
private List<Task> tasks;


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
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
@ManyToOne
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public static Project save(Project project) {
	return null;
}
public Priority getPriority() {
	return priority;
}
public void setPriority(Priority priority) {
	this.priority = priority;
}
public Progression getProgress() {
	return progress;
}
public void setProgress(Progression progress) {
	this.progress = progress;
}
public List<Task> getTasks() {
	return tasks;
}
public void setTasks(List<Task> tasks) {
	this.tasks = tasks;
}
}
