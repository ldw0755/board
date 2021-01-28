package com.company.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
//@Setter(X) - 외부에서 Set을 수행할 일이 없음
public class PageVO {	//페이지 나누기와 관련된 모든 정보를 가지고 있는 객체
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private int total;
	private Criteria cri;
	
	public PageVO(Criteria cri, int total) {
		this.total=total;	//전체 게시물 수
		this.cri = cri;		//현재 페이지 번호, 페이지당 게시물 갯수
		
		//list.jsp에서 10개 페이지씩 보여준다고 설정할 때
		//사용자 요청 : 3 = endPage : 10 => 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
		this.endPage=(int)(Math.ceil(cri.getPageNum()/10.0))*10;
		//10-9 = 1
		this.startPage = endPage-9;
		
		// 마지막 페이지가 10의 배수가 아닐때 (ex. 1, 2, 3, 4, 5 끝)
		int realEnd=(int)(Math.ceil((total/1.0)/cri.getAmount()));
		if(realEnd < this.endPage) {
			this.endPage=realEnd;
		}
		this.prev = this.startPage>1;
		this.next=this.endPage<realEnd;
	}
}
