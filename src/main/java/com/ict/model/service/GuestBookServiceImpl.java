package com.ict.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.model.dao.GuestbookDAO;
import com.ict.model.vo.GuestbookVO;

@Service
public class GuestBookServiceImpl implements GuestbookService{
	
	@Autowired
	private GuestbookDAO guestbookDAO;

	@Override
	public List<GuestbookVO> guestbookList() {
		return guestbookDAO.guestbookList();
	}

	@Override
	public int addGuestbook(GuestbookVO gvo) {
		int result = guestbookDAO.guestbookAdd(gvo);
		System.out.println("담겼는가;;;"+gvo.getName());
		return result;
	}
	@Override
	public GuestbookVO getGuestbookOneList(String idx) {
		return guestbookDAO.getGuestbookOneList(idx);
	}
	
	@Override
	public int getGuestbookDelete(String idx) {
		return guestbookDAO.getGuestbookDelete(idx);
	}
	
	
	@Override
	public int getGuestbookUpdate(GuestbookVO gvo) {
		return guestbookDAO.getGuestbookUpdate(gvo);
	}
	
	
	
	
	
}
