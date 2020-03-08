package com.sjh.todo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sjh.todo.Pagination;
import com.sjh.todo.Dto.TodoDto;
import com.sjh.todo.service.ITodoService;

@Controller
public class TodoController {
	
	@Autowired
	ITodoService service;
	
	@ModelAttribute("cp")
	public String getContextPath(HttpServletRequest request) {
		return request.getContextPath();
	}
	
	@RequestMapping("/main")
	public String list(Model model
			, @RequestParam(required = false, defaultValue = "1") int page
			, @RequestParam(required = false, defaultValue = "1") int range) {
		
		// 전체리스트 개수
        int listCnt = service.countTodo("undone");
        
        // 페이징 설정
        Pagination pagination = new Pagination();
        pagination.pageInfo(page, range, listCnt);
        
        // 전체 리스트
     	List<TodoDto> list = null;
     	
		list = service.listUpTodo("undone", pagination);
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "/main";
	}
	
	@RequestMapping("/done")
	public String done(Model model
			, @RequestParam(required = false, defaultValue = "1") int page
			, @RequestParam(required = false, defaultValue = "1") int range) {
		// 전체리스트 개수
        int listCnt = service.countTodo("done");
        
        // 페이징 설정
        Pagination pagination = new Pagination();
        pagination.pageInfo(page, range, listCnt);
        
        // 전체 리스트
     	List<TodoDto> list = null;
     	
		list = service.listUpTodo("done", pagination);
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		return "/done";
	}
	
	@RequestMapping("/writeForm")
	public String writeForm(Model model) {
		
		TodoDto dto = new TodoDto();
		model.addAttribute("dto", dto);
		
		return "/writeForm";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public ModelAndView write(@ModelAttribute("dto") TodoDto dto){
		
		ModelAndView mav = new ModelAndView();
		
		if(dto.getTitle().equals("")) {
			mav.addObject("error", "no title");
			mav.setViewName("/writeForm");
		}else if(dto.getName().equals("")) {
			mav.addObject("error", "no name");
			mav.setViewName("/writeForm");
		}else {
			service.createTodo(dto);
			mav.setViewName("redirect:/main?page=1&range=1");
		}
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/modify/{id}", method=RequestMethod.POST)
	public ResponseEntity<String> modify(@PathVariable("id") long id, @RequestBody TodoDto dto){
		ResponseEntity<String> entity = null;
		
		try {
			service.modifyTodo(dto);
			entity = new ResponseEntity<String>("modify success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id){
		
		service.removeTodo(id);
		
		return "redirect:/main?page=1&range=1";
	}
	
	@RequestMapping("/finish/{id}")
	public String finish(@PathVariable("id") long id){
		
		service.finishTodo(id);
		
		return "redirect:/main?page=1&range=1";
	}
}
