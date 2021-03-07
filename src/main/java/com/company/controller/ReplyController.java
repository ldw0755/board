package com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.domain.Criteria;
import com.company.domain.ReplyPageVO;
import com.company.domain.ReplyVO;
import com.company.service.ReplyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/replies/*")
public class ReplyController {

	@Autowired
	private ReplyService service;
	
	//댓글 삽입
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/new")
	public ResponseEntity<String> create(@RequestBody ReplyVO reply){
		log.info("댓글 삽입 "+reply);
		
		return service.regist(reply)?
				new ResponseEntity<String>("success", HttpStatus.OK):
					new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//댓글 하나 가져오기
	@GetMapping(value = "/{rno}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReplyVO> select(@PathVariable("rno") int rno){
		log.info("댓글 하나 가져오기 "+rno);
		return new ResponseEntity<ReplyVO>(service.get(rno), HttpStatus.OK);
	}
	
	@GetMapping(value = "/pages/{bno}/{page}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ReplyPageVO> getList(@PathVariable("bno") int bno, @PathVariable("page") int page){
		log.info("전체 댓글 가져오기 "+bno);
		Criteria cri = new Criteria(page, 10);
		return new ResponseEntity<ReplyPageVO>(service.getList(cri, bno), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{rno}")
	@PreAuthorize("principal.username == #reply.replyer")//username = userid
	public ResponseEntity<String> update(@PathVariable("rno")int rno, @RequestBody ReplyVO reply){
		log.info("댓글 수정 : "+rno);
		reply.setRno(rno);
		return service.update(reply)?
				new ResponseEntity<String>("success", HttpStatus.OK):
					new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/{rno}")
	@PreAuthorize("principal.username == #vo.replyer")//username = userid
	public ResponseEntity<String> remove(@PathVariable("rno") int rno, @RequestBody ReplyVO vo){
		log.info("댓글 삭제 : "+rno);
		//사용자 확인
		return service.delete(rno)?
				new ResponseEntity<String>("success", HttpStatus.OK):
					new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
