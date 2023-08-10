package com.ict.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.model.dao.FileBookDAO;
import com.ict.model.vo.FileBookVO;

@Service
public class FileBookServiceImpl implements FileBookService{
	
	@Autowired
	private FileBookDAO fileBookDAO;
	
	@Override
	public List<FileBookVO> filebooklist() {
		return fileBookDAO.filebooklist();
	}
	
	@Override
	public int addFileBook(FileBookVO fvo) {
		return fileBookDAO.filebookAdd(fvo);
	}
	
	@Override
	public FileBookVO getFileBookOneList(String idx) {
		return fileBookDAO.getFileBookOneList(idx);
	}

	@Override
	public int getFileBookDelete(String idx) {
		return 	fileBookDAO.getFileBookDelete(idx);
	}
	
	
	@Override
	public int getFileBookUpdate(FileBookVO fvo) {
		return fileBookDAO.getFileBookUpdate(fvo);
	}
	

}
