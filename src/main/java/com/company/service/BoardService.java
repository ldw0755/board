package com.company.service;

import java.util.List;

import com.company.domain.BoardVO;
import com.company.domain.Criteria;
import com.company.domain.FileAttach;

public interface BoardService {
	//게시글 등록
	public boolean regist(BoardVO board);
	//게시글 조회 - 전체
	public List<BoardVO> getList(Criteria cri);
	//게시글 조회 - 특정 번호
	public BoardVO getRow(int bno);
	//게시글 삭제
	public boolean remove(int bno);
	//게시글 수정
	public boolean modify(BoardVO board);
	//전체 게시물 수
	public int getTotalCnt(Criteria cri);
	//전체 첨부물 가져오기
	public List<FileAttach> getAttachList(int bno);
}
