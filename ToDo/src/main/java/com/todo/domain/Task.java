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
private int id;
private String title;
private String comment;
private Priority priority;
private Difficulty difficulty;
private User user;


//Getters and Setters
@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
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
public static Task save(Task task) {
	return null;
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
}
