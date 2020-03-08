package com.sjh.todo.service;

import java.util.List;

import com.sjh.todo.Pagination;
import com.sjh.todo.Dto.TodoDto;

public interface ITodoService {

	void createTodo(TodoDto dto);

	void modifyTodo(TodoDto dto);

	void removeTodo(Long id);

	List<TodoDto> listUpTodo(String type, Pagination pagination);

	void finishTodo(Long id);
	
	int countTodo(String type);

}