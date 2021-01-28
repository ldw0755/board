package com.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.company.domain.Criteria;
import com.company.domain.ReplyVO;

public interface ReplyMapper {
	public int insert(ReplyVO reply);
	public ReplyVO read(int rno);
	public List<ReplyVO> list(@Param("cri")Criteria cri, @Param("bno")int bno);
	public int countBno(int bno);
	public int update(ReplyVO reply);
	public int delete(int rno);
}
