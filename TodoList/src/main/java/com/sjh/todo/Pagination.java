package com.sjh.todo;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class Pagination {
	private int listSize = 8; // 보이는 페이지 수
	private int rangeSize = 10; // 목록의 수
	private int curPage; // 현재 페이지
	private int curRange; // 현재 목록 번호
	private int listCnt; // 총 게시글 수
	private int pageCnt; // 총 페이지 수
	private int startList; // 목록에서의 시작 게시글 번호
	private int startPage; // 목록에서의 페이지 시작 번호
	private int endPage; // 목록에서의 페이지 끝 번호
	private boolean prev; // 이전 페이지 여부
	private boolean next; // 다음 페이지 여부

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	
	public int getListSize() {
		return listSize;
	}

	public void setRangeSize(int rangeSize) {
		this.rangeSize = rangeSize;
	}
	
	public int getRangeSize() {
		return rangeSize;
	}
	
	public int getListCnt() {
		return listCnt;
	}

	public int getStartList() {
		return startList;
	}
	
	public int getCurPage() {
		return curPage;
	}

	public int getCurRange() {
		return curRange;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public boolean isNext() {
		return next;
	}

	public void pageInfo(int curPage, int curRange, int listCnt) {
		this.curPage = curPage;
		this.curRange = curPage / rangeSize + 1;
		if(curPage % rangeSize == 0) this.curRange--;
		this.listCnt = listCnt;
		
		// 전체 페이지 수
		this.pageCnt = (int)Math.ceil((double)listCnt/listSize);
	
		// 현재 목록의 시작 페이지
		this.startPage = (curRange - 1) * rangeSize + 1;
		
		// 현재 목록의 끝 페이지
		this.endPage = curRange * rangeSize;
		
		// 목록에서의 시작 게시글 번호
		this.startList = (curPage - 1) * listSize;
		
		// 이전 버튼 상태
		this.prev = curRange == 1 ? false : true;

		// 다음 버튼 상태
		this.next = pageCnt > endPage ? true : false;
		if (this.endPage > this.pageCnt) {
			this.endPage = this.pageCnt;
			this.next = false;
		}
	}
	
	public String makeQuery(int page, int range) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("range", range)
				.build();
		
		return uriComponents.toUriString();
	}
}
