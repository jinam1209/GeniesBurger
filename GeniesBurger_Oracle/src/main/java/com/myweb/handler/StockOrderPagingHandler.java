package com.myweb.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myweb.domain.CouponPageVO;
import com.myweb.domain.StockOrderPageVO;

public class StockOrderPagingHandler {
	private static Logger logger = LoggerFactory.getLogger(StockOrderPagingHandler.class);
	
	private int totalCount; // 글의 총 개수 (데이터베이스 기준)
	private int firstPageIndex; // 현재 페이지네이션의 가장 앞 번호
	private int lastPageIndex; // 현재 페이지네이션의 가장 뒷 번호
	private boolean prev, next; // 앞 뒤 단위로 이동하는 버튼의 생성 여부
	private StockOrderPageVO sopgvo;
	
	public StockOrderPagingHandler() {}
	public StockOrderPagingHandler(int totalCount, StockOrderPageVO sopgvo) {
		this.totalCount = totalCount;
		this.sopgvo = sopgvo;
		// 17번(pageIndex)을 클릭했다고 가정
		// 17/10.0 ->1.7 ->ceil(1.7) ->2.0 ->int(2.0) -> 2 => 2*10 -> 20 (마지막번호)
		this.lastPageIndex = (int)(Math.ceil(sopgvo.getPageIndex()/10.0)) * 10; 
		this.firstPageIndex = this.lastPageIndex - 9;
		// 데이터베이스에서 가져온 실제 글의 개수를 기준으로 만들어져야 하는
		// 마지막 페이지네이션의 번호
		// 실제 총 글의 개수가 134개 라고 가정
		// 마지막 페이지네이션의 번호는? =>14번
		int realLastPageIndex = (int)(Math.ceil((totalCount*1.0)/10)); // 10 = pgvo.getCountPerPage()
		
		// 연산에 의해서 만들어진 마지막 페이지네이션 인덱스 번호가
		// 실제 존재하는 총 글의 수로 만든 마지막 페이지네이션 번호보다 큰 경우
		// 조정을 해주어야 함!
		if(this.lastPageIndex >= realLastPageIndex)
			this.lastPageIndex = realLastPageIndex;
		
		this.prev = firstPageIndex > 1;
		this.next = this.lastPageIndex < realLastPageIndex;
		// 로거는 테스트 이후에 삭제!
		logger.info(">>> ? " + this.totalCount +" / " + this.firstPageIndex 
				+ " / " +sopgvo.getPageIndex() + " / " + this.lastPageIndex 
				+ " / " + sopgvo.getOrder_date()
				+ " / " + sopgvo.getRange()
				+ " / " +sopgvo.getKeyword());
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getFirstPageIndex() {
		return firstPageIndex;
	}
	public void setFirstPageIndex(int firstPageIndex) {
		this.firstPageIndex = firstPageIndex;
	}
	public int getLastPageIndex() {
		return lastPageIndex;
	}
	public void setLastPageIndex(int lastPageIndex) {
		this.lastPageIndex = lastPageIndex;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public StockOrderPageVO getSopgvo() {
		return sopgvo;
	}
	public void setSopgvo(StockOrderPageVO sopgvo) {
		this.sopgvo = sopgvo;
	}
}
