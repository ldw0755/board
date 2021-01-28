package com.company.service;

import java.util.List;

import com.company.domain.Criteria;
import com.company.domain.ReplyPageVO;
import com.company.domain.ReplyVO;

public interface ReplyService {
	public boolean regist(ReplyVO reply);
	public ReplyVO get(int rno);
	public ReplyPageVO getList(Criteria cri, int bno);
	public boolean update(ReplyVO reply);
	public boolean delete(int rno);
}
