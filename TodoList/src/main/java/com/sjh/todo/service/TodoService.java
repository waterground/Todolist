package com.sjh.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjh.todo.Pagination;
import com.sjh.todo.Dao.ITodoDao;
import com.sjh.todo.Dto.TodoDto;

@Service
public class TodoService implements ITodoService {

	@Autowired
	ITodoDao dao;

	@Override
	public void createTodo(TodoDto dto) {
		dao.todoInsert(dto);
	}

	@Override
	public void modifyTodo(TodoDto dto) {
		dao.todoUpdate(dto);
	}

	@Override
	public void removeTodo(Long id) {
		dao.todoDelete(id);
	}

	@Override
	public List<TodoDto> listUpTodo(String type, Pagination pagination) {
		List<TodoDto> list = null;

		if (type.equals("done")) {
			list = dao.getDoneList(pagination);
		} else if (type.equals("undone")) {
			list = dao.getUndoneList(pagination);
		}

		return list;
	}

	@Override
	public void finishTodo(Long id) {
		dao.finishTodo(id);
	}
	
	@Override
	public int countTodo(String type) {
		
		int result = 0;
		
		if(type.equals("done")) {
			result = dao.cntDoneTodo();
		}else {
			result = dao.cntUndoneTodo();
		}
		return result;
	}
}

