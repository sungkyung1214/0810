package com.ict.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ict.model.vo.FileBookVO;

@Repository
public class FileBookDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 리스트
	public List<FileBookVO> filebooklist() {
		return sqlSessionTemplate.selectList("filebook.list");
	}
	
	// 삽입
	public int filebookAdd(FileBookVO fvo) {
		return sqlSessionTemplate.insert("filebook.insert", fvo);
	}
	
	//하나 보기
	public FileBookVO getFileBookOneList(String idx) {
		return sqlSessionTemplate.selectOne("filebook.onelist", idx);
	}
	
	//삭제
	public int getFileBookDelete(String idx) {
		return sqlSessionTemplate.delete("filebook.delete",idx);
	}
	
	//업데이트
	public int getFileBookUpdate(FileBookVO fvo) {
		return sqlSessionTemplate.update("filebook.update", fvo);
	}
}
