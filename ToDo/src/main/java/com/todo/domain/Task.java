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
public enum Progression
{
	UNSTARTED,INPROGRESS,FINISHED
}
private Integer id;
private String name;
private String comment;
private Priority priority;
private Difficulty difficulty;
private User user;
private Progression progress;
private Project project;


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
public Progression getProgress() {
	return progress;
}
public void setProgress(Progression progress) {
	this.progress = progress;
}
@ManyToOne
public Project getProject() {
	return project;
}
public void setProject(Project project) {
	this.project = project;
}

}
