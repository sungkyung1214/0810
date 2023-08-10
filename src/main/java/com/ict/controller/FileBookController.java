package com.ict.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.model.service.FileBookService;
import com.ict.model.vo.FileBookVO;

@Controller
public class FileBookController {

	@Autowired
	private FileBookService filebookservice;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/guestbook2_list.do")
	public ModelAndView getFilebookList() {
		ModelAndView mv =new ModelAndView("filebook/list");
		List<FileBookVO> list = filebookservice.filebooklist();
		mv.addObject("list", list);
		return mv;
		
	}
	
	@GetMapping("/filebook_write.do")
	public ModelAndView writeFileBook() {
		return new ModelAndView("filebook/write");
	}
	
	@PostMapping("/filebook_writeAddForm.do")
	public ModelAndView writeOk(FileBookVO fvo, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/guestbook2_list.do");
		// 파일 업로드 처리
		try {
			String path = request.getSession().getServletContext().getRealPath("/resources/images");
						//request.getSession()=>session이다.그럴라면 위에서 http말고 세션 써야		//저장할 위치		
			
			MultipartFile f_param = fvo.getFile();
			if(f_param.isEmpty() ) {
				System.out.println("이미지 없다요");
				fvo.setF_name("");
			}else {
				// 파라미터로 받은 file을 이용해서 DB에 저장할 f_name을 채워주자
				// 그러나 같은 이름의 파일이름이면 파일을 변경해야 되므로 UUID를 이용해서 DB에 저장할 이름을 변경하자요
				UUID uuid = UUID.randomUUID();
				String f_name = uuid.toString()+"_"+fvo.getFile().getOriginalFilename();
				fvo.setF_name(f_name);
				
				// 이미지 올리기
				byte[] in = fvo.getFile().getBytes();
				File out = new File(path, f_name);
				//부모(상위폴더), 자식 
				FileCopyUtils.copy(in, out);
				
			}
			
			System.out.println("변경전;;;;"+fvo.getPwd());
			// 패스워드를 암호화 하자
			String pwd = passwordEncoder.encode(fvo.getPwd());
			fvo.setPwd(pwd);
			System.out.println("변경후;;;;"+fvo.getPwd());
			
			//DB에 저장하기
			int result = filebookservice.addFileBook(fvo);
			if(result>0) {
				return mv;				
			}else{
				return null;
			}	
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	@GetMapping("/filebook_onelist.do")
	public ModelAndView detail(String idx) {
		ModelAndView mv = new ModelAndView("filebook/onelist");
		FileBookVO fvo = filebookservice.getFileBookOneList(idx);
		mv.addObject("fvo", fvo);
		return mv;	
	}
	
	@PostMapping("/filebook_delete_Form.do")
	public ModelAndView delete(@ModelAttribute("idx") String idx) {
		ModelAndView mv = new ModelAndView("filebook/delete");
		FileBookVO fvo = filebookservice.getFileBookOneList(idx);
		mv.addObject("fvo", fvo);
		return mv;
	}
	
	@PostMapping("/filebook_delete.do")
	public ModelAndView deleteOk(String idx) {
		ModelAndView mv = new ModelAndView("redirect:/guestbook2_list.do");
		int result = filebookservice.getFileBookDelete(idx);
		return mv;
	}
	
	@PostMapping("/filebook_edit_Form.do")
	public ModelAndView edit(String idx) {
		ModelAndView mv = new ModelAndView("filebook/update");
		FileBookVO fvo = filebookservice.getFileBookOneList(idx);
		mv.addObject("fvo", fvo);
		return mv;
	}
	
	@PostMapping("/filebook_edit.do")
	public ModelAndView editOk(FileBookVO fvo, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		//비번 검사해야함 (비번이 암호와 되어있어서 그거 풀어줘야해~~)
		
		// jsp에서 암호 얻기
		String cpwd = fvo.getPwd();
		
		// DB에서 암호 얻기
		FileBookVO vo = filebookservice.getFileBookOneList(fvo.getIdx());
		String dpwd = vo.getPwd(); 
		
		//passwordEncoder.matches(암호화되지않은거, 암호화된것)
		if(! passwordEncoder.matches(cpwd, dpwd)) {
			System.out.println("암호 오류오류 오류발생");
			fvo.setF_name(fvo.getOld_f_name());
			mv.setViewName("filebook/update");
			mv.addObject("pwchk", "fail");
			mv.addObject("fvo", fvo);
			return mv;
		}else {
			System.out.println("암호 딩동댕~!");
			String path = request.getSession().getServletContext().getRealPath("/resources/images");
			try {
				MultipartFile f_param = fvo.getFile();
				String old_f_name = fvo.getOld_f_name();
				
				if(f_param.isEmpty()) {
					fvo.setF_name(old_f_name);
				}else {
					UUID uuid = UUID.randomUUID();
					String f_name = uuid.toString()+"_"+fvo.getFile().getOriginalFilename();
					fvo.setF_name(f_name);
					
					// 이미지 올리기
					byte[] in = fvo.getFile().getBytes();
					File out = new File(path, f_name);
					//부모(상위폴더), 자식 
					FileCopyUtils.copy(in, out);
				}
				int result = filebookservice.getFileBookUpdate(fvo);
				mv.setViewName("redirect:/filebook_onelist.do?idx="+fvo.getIdx());
				return mv;	
			} catch (Exception e) {
				
			}
			return null;
		}
	}
	
	@GetMapping("/filebook_down.do")
	public void download(String f_name,
			HttpServletRequest request,HttpServletResponse response) {
		// 다운로드는 void 받고 땡, return이 없음
		String path = request.getSession().getServletContext().getRealPath("/resources/images/"+f_name);
		try {
			String r_path= URLEncoder.encode(path, "utf-8");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="+r_path);
			// 여기까지 하면 실제로 다운은 안되도, 다운 되는거까지는 뜸: 브라우저에만 설정해놓은것
			
			
			File file = new File(new String(path.getBytes()));
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(in, out);
		} catch (Exception e) {
		}
	}
	
}
	


