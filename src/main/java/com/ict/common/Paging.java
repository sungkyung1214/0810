package com.ict.common;

import org.springframework.stereotype.Service;

@Service // 일처리 하는건 다 서비스
public class Paging {
	private int nowPage = 1; // 현재페이지
	private int nowBlock = 1; // 현재블록

	private int totalRecord = 0; // 전체 게시물의 수(원글의 수)

	// mariadb sql - limit해당
	private int numberPerPage = 10; // 한 게시물에 보이는 갯수
	private int pagePerBlock = 3;; // 한 블록안에 존재하는 페이지의 수

	private int totalPage = 0; // 전체 페이지의 수
	private int totalBlock = 0; // 전체 블록의 수

	private int begin = 0;
	private int end = 0;

	private int beginBlock = 0;
	private int endBlock = 0;

	private int offset = 0; //begin,end 대신에 offset 으로

	
	
	
	
	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getNowBlock() {
		return nowBlock;
	}

	public void setNowBlock(int nowBlock) {
		this.nowBlock = nowBlock;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getNumberPerPage() {
		return numberPerPage;
	}

	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	}

	public int getPagePerBlock() {
		return pagePerBlock;
	}

	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalBlock() {
		return totalBlock;
	}

	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getBeginBlock() {
		return beginBlock;
	}

	public void setBeginBlock(int beginBlock) {
		this.beginBlock = beginBlock;
	}

	public int getEndBlock() {
		return endBlock;
	}

	public void setEndBlock(int endBlock) {
		this.endBlock = endBlock;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
