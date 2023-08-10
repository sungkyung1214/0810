package com.ict.model.service;

import java.util.List;

import com.ict.model.vo.FileBookVO;

public interface FileBookService {

	//전체보기
	List<FileBookVO> filebooklist();
	
	//상세보기
	FileBookVO getFileBookOneList(String idx);
	
	//삽입
	int addFileBook(FileBookVO fvo);
	
	//삭제
	int getFileBookDelete(String idx);
	
	//수정
	int getFileBookUpdate(FileBookVO fvo);
}
