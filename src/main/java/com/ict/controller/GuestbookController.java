package com.ict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.ict.model.service.GuestbookService;
import com.ict.model.vo.GuestbookVO;


@Controller
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@GetMapping("/guestbook_list.do")
	public ModelAndView getGuestbookList() {
		ModelAndView mv = new ModelAndView("guestbook/guestbookList");
		List<GuestbookVO> list = guestbookService.guestbookList();
		mv.addObject("list", list);
		return mv;
	}
	
	@GetMapping("/guestbook_write.do")
	public ModelAndView writeGuestbook() {
		return new ModelAndView("guestbook/write");	
	}
	
	@PostMapping(value = "/guestbook_writeOK.do")
	public ModelAndView writeOkGuestbook(GuestbookVO gvo) {
		ModelAndView mv = new ModelAndView("redirect:/guestbook_list.do");
		int result = guestbookService.addGuestbook(gvo);
		// 여기에 그림도 같이 넣어서 보내줘야함
		return mv;
		
	}
	
	// idx는 onelist.jsp에도 사용하기 때문에 넘겨야 한다.
	@GetMapping("/guestbook_onelist.do") //String idx 에 넘어온값이 저장되기 때문에 꼭 똑같이 만들어줘야한다.
	public ModelAndView getOneList(@ModelAttribute("idx") String idx) { //파라미터로 넘어온 아이디엑스를 스트링으로 새로운 idx에 받게따
		ModelAndView mv = new ModelAndView("guestbook/onelist");
		GuestbookVO gvo = guestbookService.getGuestbookOneList(idx);//하나만 받아오니까 리스트 아니고 vo
		mv.addObject("gvo", gvo);
		return mv;
	}
	
	
	@PostMapping("/gusetbook_delete_Form.do")
	public ModelAndView getGuestbookdeleteForm(@ModelAttribute("idx") String idx) {
		ModelAndView mv = new ModelAndView("guestbook/delete");
		// jsp 실제 삭제할때 비번 검사하기 위해서 getguestbookOnelist()를 실행하자
		GuestbookVO gvo = guestbookService.getGuestbookOneList(idx);
		mv.addObject("gvo", gvo);
		return mv;
	}
	
	
	@PostMapping("/gusetbook_delete.do")
	public ModelAndView getGuestbookdeleteOk(String idx) {
		ModelAndView mv = new ModelAndView("redirect:/guestbook_list.do");
		int result = guestbookService.getGuestbookDelete(idx);
		return mv;
	}
	

	
	@PostMapping("/gusetbook_edit_Form.do")
	public ModelAndView getGuestbookEditForm(@ModelAttribute("idx") String idx) {
		ModelAndView mv = new ModelAndView("guestbook/update");
		GuestbookVO gvo = guestbookService.getGuestbookOneList(idx);
		mv.addObject("gvo", gvo);
		return mv;
	}

	@PostMapping("/guestbook_edit.do")
	public ModelAndView getGuestbookEditOk(GuestbookVO gvo) {
		ModelAndView mv = new ModelAndView("redirect:/guestbook_onelist.do?idx="+gvo.getIdx());
		int result =guestbookService.getGuestbookUpdate(gvo);
		return mv;
	}
	
	
}
