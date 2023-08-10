package com.ict.bbs.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ict.bbs.model.vo.BBSVO;
import com.ict.bbs.model.vo.CommentVO;
import com.ict.model.vo.GuestbookVO;

@Service
public interface BBSService{

	// 리스트 보기
	List<BBSVO> getbbsList();	
	// 삽입
	int addList(BBSVO bbsvo);
	
	// 전체 게시물의 수
	public int getTotalCount();
	// 페이징처리를 위한 리스트
	public List<BBSVO> getList(int offset, int limit);
	
	// 상세보기
	BBSVO getOneList(String b_idx);
	// 조회수 업데이트
	int getHitUpdate(String b_idx);
	// 코멘트 가져오기
	public List<CommentVO> getCommList(String b_idx);
	// 코멘트 삽입하기
	public int getCommInsert(CommentVO cvo);
	// 코멘트 삭제
	public int getCommDelete(String c_idx);
	// 원글 삭제
	public int getDelete(String b_idx);
	// 원글 수정
	public int getUpdate(BBSVO bvo);
	
}
