package com.ict.model.service;

import java.util.List;

import com.ict.model.vo.GuestbookVO;

public interface GuestbookService {
	
	//전체보기
	List<GuestbookVO> guestbookList();
	
	// 상세보기
	GuestbookVO getGuestbookOneList(String idx);
	
	
	//삽입
	int addGuestbook(GuestbookVO gvo);
	
	
	// 삭제
	int getGuestbookDelete(String idx);
	
	//수정
	int getGuestbookUpdate(GuestbookVO gvo);
	

}
