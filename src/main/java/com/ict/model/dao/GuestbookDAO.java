package com.ict.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ict.model.vo.GuestbookVO;

@Repository
public class GuestbookDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 서비스에서 db처리하는 메서드를 모두 받아줘야 한다.
	
	// 리스트
	public List<GuestbookVO> guestbookList() {
		return sqlSessionTemplate.selectList("guestbook.list");			
	}
	
	// 삽입
	public int guestbookAdd(GuestbookVO gvo) {
		return sqlSessionTemplate.insert("guestbook.insert", gvo);
		
	}
	
	// 상세보기
	public GuestbookVO getGuestbookOneList(String idx){
		return sqlSessionTemplate.selectOne("guestbook.onelist", idx);
	}
	
	// 삭제
	public int getGuestbookDelete(String idx) {
		return sqlSessionTemplate.delete("guestbook.delete", idx);
	}
	
	//수정
	public int getGuestbookUpdate(GuestbookVO gvo) {
		return sqlSessionTemplate.update("guestbook.update",gvo);
	}
}