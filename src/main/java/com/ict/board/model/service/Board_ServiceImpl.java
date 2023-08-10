package com.ict.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.board.model.dao.Board_DAO;
import com.ict.board.model.vo.Board_VO;

@Service
public class Board_ServiceImpl implements Board_Service{
		
	@Autowired
	private Board_DAO board_DAO;
	
	@Override
	public int getTotalCount() {
		return board_DAO.getTotalCount();
	}
	@Override
	public List<Board_VO> getList(int offset, int limit) {
		return board_DAO.getList(offset,limit);
	}
	
	@Override
	public int getInsert(Board_VO bv) {
		return board_DAO.getInsert(bv);
	}
	
	@Override
	public int getHitUpdate(String idx) {
		return board_DAO.getHitUpdate(idx);
	}
	
	@Override
	public Board_VO getOneList(String idx) {
		return board_DAO.getOneList(idx);
	}

}
