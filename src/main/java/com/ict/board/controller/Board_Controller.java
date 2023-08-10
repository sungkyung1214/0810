package com.ict.board.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.board.model.service.Board_Service;
import com.ict.board.model.vo.Board_VO;
import com.ict.common.Paging;

@Controller
public class Board_Controller {
	
	@Autowired
	private Board_Service board_Service;	
	@Autowired
	private Paging paging;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@RequestMapping("/board_list.do")
	public ModelAndView boardList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("board/board_list");
		
		// 전체 게시물의 수
		int count = board_Service.getTotalCount();
		paging.setTotalRecord(count);
		
		//전체 페이지의 수 
		if(paging.getTotalRecord() <= paging.getNumberPerPage()) {
						//10개라고 정해놓음, 10개보다 적으면 그냥 1페이지로만 보임
			paging.setTotalPage(1);
		}else {
			paging.setTotalPage(paging.getTotalRecord()/paging.getNumberPerPage());
			if(paging.getTotalRecord()%paging.getNumberPerPage() != 0) {
				paging.setTotalPage(paging.getTotalPage()+1);
			}
		}
		
		//현재 페이지
		String cPage = request.getParameter("cPage"); //
		if(cPage == null) {
			paging.setNowPage(1);
		}else {
			paging.setNowPage(Integer.parseInt(cPage));
		}
		
		
		//begin end 대신 limit,offset
		// limit = paging.getNumPerPage()
		//offset = limit * (paging.getNowPage()-1)
		paging.setOffset(paging.getNumberPerPage()*(paging.getNowPage()-1));

		paging.setBeginBlock((int)((paging.getNowPage()-1)/paging.getPagePerBlock())
		*paging.getPagePerBlock()+1);	
		
		paging.setEndBlock(paging.getBeginBlock()+paging.getPagePerBlock()-1);
		
		//주의사항 ( endBlock이 totoalpage보다 클때가 있다.)
		if(paging.getEndBlock()>paging.getTotalPage()) {
			paging.setEndBlock(paging.getTotalPage());
		}
	
											//페이지의 시작값, limit
		List<Board_VO> board_list = board_Service.getList(paging.getOffset(),paging.getNumberPerPage());
		mv.addObject("board_list", board_list);
		mv.addObject("paging", paging);
		return mv;
		
	}
	
	@GetMapping("/board_insertForm.do")
	public ModelAndView boardInsertForm() {
		return new ModelAndView("board/board_write");
		
	}
	
	@RequestMapping("/board_insert.do")
	public ModelAndView boardInsert(Board_VO bv, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/board_list.do");
		try {
			String path = request.getSession().getServletContext().getRealPath("/resources/images");
			MultipartFile file = bv.getFile();
			if(file.isEmpty()) {
				bv.setF_name("");
			}else {
				// 같은 이름 없도록 uuid 사용
				UUID uuid= UUID.randomUUID();
				String f_name = uuid.toString()+"_"+bv.getFile().getOriginalFilename();
				bv.setF_name(f_name);
				
				//이미지 저장
				byte[] in = bv.getFile().getBytes();
				File out = new File(path, f_name);
				FileCopyUtils.copy(in, out);
			}
			
			//패스워드 암호화
			bv.setPwd(passwordEncoder.encode(bv.getPwd()));
			int res = board_Service.getInsert(bv);
			return mv;
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}
	
	@GetMapping("/board_oneList.do")
	public ModelAndView boardOneList(
			@ModelAttribute("cPage")String cPage,
			@ModelAttribute("idx")String idx) {
		ModelAndView mv = new ModelAndView("board/board_onelist");
	
		// 한번에 일하는데 디비를 2번 갔다오면 transaction 처리를 해줘야한다.
		
		// 조회수 업데이트
		int hit = board_Service.getHitUpdate(idx);
		
		// 상세보기
		Board_VO bv = board_Service.getOneList(idx);
		mv.addObject("bv", bv);
		
		
		return mv;
	}
}

