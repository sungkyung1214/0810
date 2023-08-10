package com.ict.board.model.service;

import java.util.List;

import com.ict.board.model.vo.Board_VO;

public interface Board_Service {
	
	// 리스트보기
	public List<Board_VO> getList(int offset, int limit);
	// 전체 게시물의 수
	public int getTotalCount();
	
	//삽입
	int getInsert(Board_VO bv);
	
	//조회수 업데이트
	int getHitUpdate(String idx);
	
	//상세보기
	Board_VO getOneList(String idx);

}
