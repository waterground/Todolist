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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	public String list(Model model) {
		List<TodoDto> list = null;
		
		list = service.listUpTodo("undone");
		model.addAttribute("list", list);
		
		return "/main";
	}
	
	@RequestMapping("/done")
	public ModelAndView done() {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("list", service.listUpTodo("done"));
		mav.setViewName("/done");
		
		return mav;
	}
	
	@RequestMapping("/writeForm")
	public String writeForm(Model model) {
		
		TodoDto dto = new TodoDto();
		model.addAttribute("dto", dto);
		
		return "/writeForm";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@ModelAttribute TodoDto dto){
		
		service.createTodo(dto);
		
		return "redirect:/main";
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
		
		return "redirect:/main";
	}
	
	@RequestMapping("/finish/{id}")
	public String finish(@PathVariable("id") long id){
		
		service.finishTodo(id);
		
		return "redirect:/main";
	}
}
