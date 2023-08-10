package com.ict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.model.service.MembersService;
import com.ict.model.vo.MembersVO;

@Controller
public class MembersController {
	
	@Autowired
	private MembersService membersService;

	@GetMapping("/members_list.do")
	public ModelAndView getMembersList() {
		ModelAndView mv = new ModelAndView("members/list");
		List<MembersVO> list = membersService.membersList();
		mv.addObject("list", list);
		return mv;
		
	}
	
	@GetMapping("/members_addForm.do")
	public ModelAndView getMemberAddForm() {
		return new ModelAndView("members/addForm");	
	}
	
	@PostMapping("/members_addMember.do")
	//넘어오는 파라미터가 있다. 있는데 스프링에서는 파라미터 값을vo에 받는다 
	public ModelAndView getMemberAdd(MembersVO mvo) {
		// 리다이렉트: 리스트를 다시 보여준다.
		ModelAndView mv= new ModelAndView("redirect:/members_list.do");
		int result = membersService.memberAdd(mvo);
		return mv;
	}
	
}
