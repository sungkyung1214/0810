package com.ict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.model.service.FileBookService;

@Controller
public class DownController {

	@Autowired
	private FileBookService filebookservice;
	
	@PostMapping("/filebook_fileupload")
	public ModelAndView file() {
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
}
