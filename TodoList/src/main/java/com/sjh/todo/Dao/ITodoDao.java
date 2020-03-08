package com.sjh.todo.Dao;

import java.util.List;

import com.sjh.todo.Pagination;
import com.sjh.todo.Dto.TodoDto;

public interface ITodoDao {

	List<TodoDto> getDoneList(Pagination pagination);

	List<TodoDto> getUndoneList(Pagination pagination);

	int todoUpdate(TodoDto dto);

	int todoDelete(long id);

	int todoInsert(TodoDto dto);

	int finishTodo(long id);
	
	int cntDoneTodo();
	
	int cntUndoneTodo();

}