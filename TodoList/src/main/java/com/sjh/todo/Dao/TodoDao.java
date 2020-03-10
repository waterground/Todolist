package com.sjh.todo.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sjh.todo.Pagination;
import com.sjh.todo.Dto.TodoDto;

@Repository
public class TodoDao implements ITodoDao {

	private JdbcTemplate template;
	
	public TodoDao(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public List<TodoDto> getDoneList(final Pagination pagination) {
		List<TodoDto> list = null;

		final String sql = "SELECT * FROM todo WHERE result IS NOT NULL ORDER BY goalDate LIMIT ?, ?;";
		
		list = template.query(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setObject(1, pagination.getStartList());
				pstmt.setObject(2, pagination.getListSize());
				return pstmt;
			}
		}, new RowMapper<TodoDto>() {
			@Override
			public TodoDto mapRow(ResultSet rs, int rowNum) throws SQLException {

				TodoDto dto = new TodoDto();
				dto.setId(rs.getLong("id"));
				dto.setTitle(rs.getString("title"));
				dto.setName(rs.getString("name"));
				dto.setPriority(rs.getString("priority"));
				dto.setRegDate(rs.getString("regDate"));
				dto.setGoalDate(rs.getString("goalDate"));
				dto.setResult(rs.getBoolean("result"));

				return dto;
			}
		});

		return list;
	}

	@Override
	public List<TodoDto> getUndoneList(final Pagination pagination) {
		List<TodoDto> list = null;

		final String sql = "SELECT * FROM todo WHERE result IS NULL ORDER BY id LIMIT ?, ?;";

		list = template.query(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setObject(1, pagination.getStartList());
				pstmt.setObject(2, pagination.getListSize());
				return pstmt;
			}
		}, new RowMapper<TodoDto>() {
			@Override
			public TodoDto mapRow(ResultSet rs, int rowNum) throws SQLException {

				TodoDto dto = new TodoDto();
				dto.setId(rs.getLong("id"));
				dto.setTitle(rs.getString("title"));
				dto.setName(rs.getString("name"));
				dto.setPriority(rs.getString("priority"));
				dto.setRegDate(rs.getString("regDate"));
				dto.setGoalDate(rs.getString("goalDate"));
				dto.setResult(rs.getBoolean("result"));

				return dto;
			}
		});

		return list;
	}

	@Override
	public int todoUpdate(final TodoDto dto) {

		int result = 0;

		String sql = "UPDATE todo SET title=?, name=?, priority=?, goalDate=? WHERE id=?;";

		result = template.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, dto.getTitle());
				pstmt.setString(2, dto.getName());
				pstmt.setString(3, dto.getPriority());
				pstmt.setString(4, dto.getGoalDate());
				pstmt.setLong(5, dto.getId());
			}
		});

		return result;
	}

	@Override
	public int todoDelete(final long id) {
		int result = 0;

		String sql = "DELETE FROM todo WHERE id=?;";

		result = template.update(sql, id);

		return result;
	}

	@Override
	public int todoInsert(final TodoDto dto) {
		int result = 0;

		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				
		Date time = new Date();
				
		String regDate = format1.format(time);
		
		String sql = "INSERT INTO todo (title, name, priority, regDate, goalDate) VALUES (?, ?, ?, ?, ?);";

		result = template.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, dto.getTitle());
				pstmt.setString(2, dto.getName());
				pstmt.setString(3, dto.getPriority());
				pstmt.setString(4, regDate);
				pstmt.setString(5, dto.getGoalDate());
			}
		});

		return result;
	}
	
	@Override
	public int finishTodo(final long id) {
		int result = 0;

		String sql = "UPDATE todo SET result=(CASE "
				+ "WHEN DATEDIFF(goalDate, now()) >= 0 THEN true "
				+ "ELSE false END) WHERE id=?;";

		result = template.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setLong(1, id);
			}
		});
		
		return result;
	}
	
	@Override
	public int cntDoneTodo() {
		int result = 0;
		
		String sql = "SELECT COUNT(*) FROM todo WHERE result IS NOT NULL;";
		
		result = template.queryForObject(sql, Integer.class);
		
		return result;
	}
	
	@Override
	public int cntUndoneTodo() {
		int result = 0;
		
		String sql = "SELECT COUNT(*) FROM todo WHERE result IS NULL;";
		
		result = template.queryForObject(sql, Integer.class);
		
		return result;
	}
}
