package com.sjh.todo.Dto;

public class TodoDto {
	private long id;
	private String title;
	private String name;
	private String priority;
	private String regDate;
	private String goalDate;
	private boolean result;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getGoalDate() {
		return goalDate;
	}
	public void setGoalDate(String goalDate) {
		this.goalDate = goalDate;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
}
