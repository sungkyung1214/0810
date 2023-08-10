package com.ict.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ict.board.model.vo.Board_VO;

@Repository
public class Board_DAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public int getTotalCount() {
		return sqlSessionTemplate.selectOne("board.count");
	}
	
	public List<Board_VO> getList(int offset, int limit) {
		//파라미터가 2개인데 그걸 한꺼번에 넣을 수 없어서 map을 사용한다.
		Map<String, Integer>map = new HashMap<String, Integer>();
		map.put("limit", limit);
		map.put("offset", offset);
		return sqlSessionTemplate.selectList("board.list",map);
	}
	
	public int getInsert(Board_VO bv) {
		return sqlSessionTemplate.insert("board.insert", bv);
	}
	
	public int getHitUpdate(String idx) {
		return sqlSessionTemplate.update("board.hitup", idx);
	}
	
	public Board_VO getOneList(String idx) {
		return sqlSessionTemplate.selectOne("board.onelist", idx);
	}
}
