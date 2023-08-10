package com.ict.bbs.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ict.bbs.model.vo.BBSVO;
import com.ict.bbs.model.vo.CommentVO;

@Repository
public class BBSDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int getTotalCount(){
		return sqlSessionTemplate.selectOne("bbs.count");
	}
	
	public List<BBSVO> getList(int offset, int limit){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("limit", limit);
		map.put("offset", offset);	
		return sqlSessionTemplate.selectList("bbs.list",map);
	}
	
	public List<BBSVO> getbbsList(){		
		return sqlSessionTemplate.selectList("bbs.list");
	}
	
	public int getAddList(BBSVO bbsvo) {
		return sqlSessionTemplate.insert("bbs.insert",bbsvo);
	}
	
	public int getHitUpdate(String b_idx) {
		return sqlSessionTemplate.update("bbs.hitup", b_idx);
	}

	public BBSVO getOneList(String b_idx) {
		return sqlSessionTemplate.selectOne("bbs.onelist", b_idx);
	}
	
	public List<CommentVO> getCommList(String b_idx) {
		return sqlSessionTemplate.selectList("bbs.comlist", b_idx);
	}
	
	public int getCommInsert(CommentVO cvo) {
		return sqlSessionTemplate.insert("bbs.cominsert",cvo);
	}
	
	public int getCommDelete(String c_idx) {
		return sqlSessionTemplate.delete("bbs.comdelete",c_idx);
	}
	
	public int getDelete(String b_idx) {
		return sqlSessionTemplate.update("bbs.delete",b_idx);
	}
	
	public int getUpdate(BBSVO bvo) {
		return sqlSessionTemplate.update("bbs.update", bvo);
	}
}
