package com.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.company.domain.BoardVO;
import com.company.domain.Criteria;
import com.company.domain.FileAttach;

public interface BoardMapper {
	public int insert(BoardVO board);
	public int delete(int bno);
	public int update(BoardVO board);
	public List<BoardVO> list(Criteria cri);
	public BoardVO read(int bno);
	public int totalCnt(Criteria cri);
	public int updateReplyCnt(@Param("bno")int bno, @Param("amount")int amount);
	//첨부물 가져오기
	public List<FileAttach> attachList(int bno);
}
