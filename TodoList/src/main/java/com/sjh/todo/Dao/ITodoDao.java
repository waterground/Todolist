package com.sjh.todo.Dao;

import java.util.List;

import com.sjh.todo.Dto.TodoDto;

public interface ITodoDao {

	List<TodoDto> getDoneList();

	List<TodoDto> getUndoneList();

	int todoUpdate(TodoDto dto);

	int todoDelete(long id);

	int todoInsert(TodoDto dto);

	int finishTodo(long id);

}