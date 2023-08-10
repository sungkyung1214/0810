package com.ict.bbs.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.bbs.model.dao.BBSDAO;
import com.ict.bbs.model.vo.BBSVO;
import com.ict.bbs.model.vo.CommentVO;



@Service
public class BBSServiceImpl implements BBSService{
	
	@Autowired
	private BBSDAO bBSDAO;
	
	@Override
	public List<BBSVO> getbbsList() {
		return bBSDAO.getbbsList();
	}

	@Override
	public int addList(BBSVO bbsvo) {
		return bBSDAO.getAddList(bbsvo);
	}
	
	@Override
	public BBSVO getOneList(String b_idx) {
		return bBSDAO.getOneList(b_idx);
	}
	
	@Override
	public int getHitUpdate(String b_idx) {
		return bBSDAO.getHitUpdate(b_idx);
	}
	
	@Override
	public int getTotalCount() {
		return bBSDAO.getTotalCount();
	}
	
	@Override
	public List<BBSVO> getList(int offset, int limit) {
		return bBSDAO.getList(offset,limit);
	}
	
	@Override
	public List<CommentVO> getCommList(String b_idx) {
		return bBSDAO.getCommList(b_idx);
	}
	
	@Override
	public int getCommInsert(CommentVO cvo) {
		return bBSDAO.getCommInsert(cvo);
	}
	
	@Override
	public int getCommDelete(String c_idx) {
		return bBSDAO.getCommDelete(c_idx);
	}
	
	@Override
	public int getDelete(String b_idx) {
		return bBSDAO.getDelete(b_idx);
	}
	
	@Override
	public int getUpdate(BBSVO bvo) {
		return bBSDAO.getUpdate(bvo);
	}
}
