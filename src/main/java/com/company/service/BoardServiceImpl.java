package com.company.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.domain.BoardVO;
import com.company.domain.Criteria;
import com.company.domain.FileAttach;
import com.company.mapper.AttachMapper;
import com.company.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper mapper;
	@Autowired
	private AttachMapper attachMapper;

	@Transactional
	@Override
	public boolean regist(BoardVO board) {

		boolean result = mapper.insert(board) > 0 ? true : false;

		// 첨부파일이 null이거나 size()가 0이라면
		// return false - 그 이후 명령문을 수행하지 않는다.
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return result;
		}

		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
		return result;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		return mapper.list(cri);
	}

	@Override
	public BoardVO getRow(int bno) {
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public boolean remove(int bno) {
		
		//첨부물 삭제
		attachMapper.delete(bno);
		//게시글 삭제
		return mapper.delete(bno) > 0 ? true : false;
	}

	@Override
	public boolean modify(BoardVO board) {
		// 첨부물 전체 삭제
		attachMapper.delete(board.getBno());
		//게시물 수정
		boolean result = mapper.update(board) > 0 ? true : false;

		// 첨부파일이 null이거나 size()가 0이라면
		// return false - 그 이후 명령문을 수행하지 않는다.
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return result;
		}
		//첨부물 삽입
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
		return result;
	}

	@Override
	public int getTotalCnt(Criteria cri) {
		return mapper.totalCnt(cri);
	}

	@Override
	public List<FileAttach> getAttachList(int bno) {
		return mapper.attachList(bno);
	}
}
