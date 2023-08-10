package com.ict.bbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.bbs.model.service.BBSService;
import com.ict.bbs.model.vo.BBSVO;
import com.ict.bbs.model.vo.CommentVO;
import com.ict.common.Paging;

@Controller
public class BBSController {
	
	@Autowired
	private BBSService bbsservice;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private Paging paging;
	
	@RequestMapping("/bbs_list.do")
	public ModelAndView bbsList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("bbs/list");
		
		// 전체 게시물의 수 
		int count = bbsservice.getTotalCount();
		paging.setTotalRecord(count);
		
		// 전체 페이지의 수 
		if(paging.getTotalRecord() <= paging.getNumberPerPage()){
			paging.setTotalPage(1);
		}else {
			paging.setTotalPage(paging.getTotalRecord()/paging.getNumberPerPage());
			if(paging.getTotalRecord()%paging.getNumberPerPage() != 0) {
				paging.setTotalPage(paging.getTotalPage()+1);
			}
		}
		
		// 현재 페이지
		String cPage = request.getParameter("cPage");
		if(cPage == null) {
			paging.setNowPage(1);
		}else {
			paging.setNowPage(Integer.parseInt(cPage));
		}
		
		// begin, end 대신 limit, offset
		// limit = paging.getNumPerPage()
		
		// offset = limit*(현재페이지)-1
		paging.setOffset(paging.getNumberPerPage()*(paging.getNowPage()-1));
		
		// 시작블록과 끝블록 구하기
		paging.setBeginBlock((int)((paging.getNowPage()-1)/paging.getPagePerBlock())
				*paging.getPagePerBlock() +1);
		
		paging.setEndBlock(paging.getBeginBlock()+paging.getPagePerBlock()-1);
		
		// 주의사항 (같게 만들어줘야함)
		if(paging.getEndBlock()>paging.getTotalPage()) {
			paging.setEndBlock(paging.getTotalPage());
		}
		
		List<BBSVO> bbs_list = bbsservice.getList(paging.getOffset(),paging.getNumberPerPage());
		mv.addObject("bbs_list", bbs_list);
		mv.addObject("paging", paging);
		return mv;
	}
	
	@GetMapping("/bbs.write.do")
	public ModelAndView write() {
		ModelAndView mv = new ModelAndView("bbs/write");
		return mv;
	}
	
	@PostMapping("/bbs_list_again.do")
	public ModelAndView listAgain() {
		ModelAndView mv = new ModelAndView("redirect:/bbs_list.do");
		return mv;
	}
	
	@PostMapping("/bbs_writeOK.do")
	public ModelAndView getAddList(BBSVO bbsvo, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("redirect:/bbs_list.do");
		try {
			String path = request.getSession().getServletContext().getRealPath("/resources/images");
			MultipartFile file = bbsvo.getFile();
			if(file.isEmpty()) {
				bbsvo.setF_name("");
			}else {
				// 같은 이름의 파일 이름이 없도록
				UUID uuid = UUID.randomUUID();
				String f_name =uuid.toString()+"_"+bbsvo.getFile().getOriginalFilename();
				bbsvo.setF_name(f_name);
				
				// 이미지 저장
				byte[] in = bbsvo.getFile().getBytes();
				File out = new File(path, f_name);
				FileCopyUtils.copy(in, out);
			}
			// 패스워드 암호화
			String pwd = passwordEncoder.encode(bbsvo.getPwd());
			bbsvo.setPwd(pwd);
			//bbsvo.setPwd(passwordEncoder.encode(bbsvo.getPwd()));  => 한줄로 만들기
			int result = bbsservice.addList(bbsvo);
			if(result>0) {
				return mv;				
			}else {
				return null;				
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	
	@GetMapping("/bbs_onelist.do")
	public ModelAndView bbsOneList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("bbs/onelist");
		String b_idx = request.getParameter("b_idx");
		String cPage = request.getParameter("cPage");
		
		// 조회수 업데이트
		int res = bbsservice.getHitUpdate(b_idx);
		
		// 상세보기
		BBSVO bvo = bbsservice.getOneList(b_idx);
		
		// 댓글 가져오기
		List<CommentVO> c_list = bbsservice.getCommList(b_idx);
		
		mv.addObject("c_list", c_list);
		mv.addObject("bvo", bvo);
		mv.addObject("cPage", cPage);
		return mv;
	}
	
	/*
	 * //전통적인 방법 =jsp
	 * 
	 * @PostMapping("/com_insert.do") public ModelAndView commetnInsert(CommentVO
	 * cvo, HttpServletRequest request) { String cPage =
	 * request.getParameter("cPage");
	 * 
	 * // 원래는 안 받아도 된다. 근데 밑에서 써야하니까 귀찮아서 한번더 써준거 // 귀찮게 쓸려면 cvo.getB_idx(); String
	 * b_idx = request.getParameter("b_idx");
	 * 
	 * ModelAndView mv = new
	 * ModelAndView("redirect:/bbs_onelist.do?b_idx="+b_idx+"&cPage="+cPage);
	 * 
	 * int result = bbsservice.getCommInsert(cvo); return mv;
	 * 
	 * }
	 */
	
	//spring 방식
	//@ModelAttribute("cPage")String cPage,
	// 파라미터 cPage를 받아서 model에 cPage라는 이름으로 저장된다.
	// 다음에 넘어갈 페이지에게 전달
	  
	@PostMapping("/com_insert.do") 
	public ModelAndView commentInsert(CommentVO cvo,
	  @ModelAttribute("cPage")String cPage, @ModelAttribute("b_idx")String b_idx) {
	  ModelAndView mv = new ModelAndView("redirect:/bbs_onelist.do"); 
	  int result = bbsservice.getCommInsert(cvo);
	  return mv;
	 
	  }
	
	@PostMapping("/com_delete.do") 
	public ModelAndView commentDelete(
			@RequestParam("c_idx")String c_idx,
			@ModelAttribute("cPage")String cPage, 
			@ModelAttribute("b_idx")String b_idx) {
	  ModelAndView mv = new ModelAndView("redirect:/bbs_onelist.do"); 
	  System.out.println(c_idx);
	  int result = bbsservice.getCommDelete(c_idx);
	  return mv;
	 
	 }
	
	
	
	
	@PostMapping("/bbs_deleteForm.do") 
	public ModelAndView deleteForm(
			@ModelAttribute("cPage")String cPage, 
			@ModelAttribute("b_idx")String b_idx) {
	  ModelAndView mv = new ModelAndView("bbs/delete"); 
	  return mv;
	 
	 }
	
	@PostMapping("/bbs_delete.do")
	public ModelAndView bbsDelete(
			@RequestParam("pwd") String pwd, 
			@ModelAttribute("cPage") String cPage,
			@ModelAttribute("b_idx") String b_idx) {
		ModelAndView mv = new ModelAndView();

		// 비빌 번호가 맞는지 체크 하자 .
		// DB에서 암호 얻기
		BBSVO bvo = bbsservice.getOneList(b_idx);
		String dbpwd = bvo.getPwd();

		// passwordEncoder.matches(암호화되지 않은것, 암호화 된것 )
		if (!passwordEncoder.matches(pwd, dbpwd)) {
			mv.setViewName("bbs/delete");
			mv.addObject("pwchk", "fail");
			return mv;
		} else {
			// 원글삭제 시 상태값을 0 => 1 로 변경 시키자
			int result = bbsservice.getDelete(b_idx);
			mv.setViewName("redirect:/bbs_list.do");
			return mv;
		}
	}
	
	@PostMapping("/bbs_updateForm.do")
	public ModelAndView bbsUpdateForm(
			@ModelAttribute("cPage")String cPage, 
			@ModelAttribute("b_idx")String b_idx) {
		ModelAndView mv = new ModelAndView("bbs/update");
		BBSVO bvo = bbsservice.getOneList(b_idx);
		mv.addObject("bvo", bvo);
		return mv;
	}
	
	@PostMapping("/bbs_update.do")
	public ModelAndView bbsUpdateForm(
			BBSVO bvo, HttpServletRequest request,
			@ModelAttribute("cPage")String cPage, 
			@ModelAttribute("b_idx")String b_idx) {
		
		ModelAndView mv = new ModelAndView();
		
		// 비밀번호 검사 : 비밀번호 안 맞으면 밑에 할 필요 x
		// 비밀 번호가 맞는지 체크
		// DB에서 암호얻기
		BBSVO bvo2 = bbsservice.getOneList(b_idx);
		String dbpwd = bvo2.getPwd();
				
		// passwordEncoder.matches(암호화되지 않은것, 암호화된것)
		if(! passwordEncoder.matches(bvo.getPwd(), dbpwd)) {
			mv.setViewName("bbs/update");
			mv.addObject("pwchk", "fail");
			mv.addObject("bvo", bvo);
			return mv;
		}else {
			try {
			String path = request.getSession().getServletContext().getRealPath("/resources/images");
			MultipartFile file = bvo.getFile();
				if(file.isEmpty()) {
				bvo.setF_name(bvo.getOld_f_name());
				}else {
				// 같은 이름의 파일 이름이 없도록
				UUID uuid = UUID.randomUUID();
				String f_name =uuid.toString()+"_"+bvo.getFile().getOriginalFilename();
				bvo.setF_name(f_name);
				
				// 이미지 저장
				byte[] in = bvo.getFile().getBytes();
				File out = new File(path, f_name);
				FileCopyUtils.copy(in, out);
				}
				// 패스워드 암호화
			String pwd = passwordEncoder.encode(bvo.getPwd());
			bvo.setPwd(pwd);
			//bbsvo.setPwd(passwordEncoder.encode(bbsvo.getPwd()));  => 한줄로 만들기
			int result = bbsservice.getUpdate(bvo);
				if(result>0) {
					mv.setViewName("redirect:/bbs_onelist.do");
					return mv;				
				}else {
					return null;				
				}
			} catch (Exception e) {
				System.out.println(e);
			return null;
			}
		}
	}	
	
	
	@GetMapping("/down.do")
	public void down(
			@RequestParam("f_name")String f_name,
			HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String path = request.getSession().getServletContext().getRealPath("/resources/images/"+f_name);
			String r_path = URLEncoder.encode(path, "utf-8");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="+r_path);
			
			File file = new File(new String(path.getBytes()));
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(in, out);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
	}
}

