package com.company.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.domain.BoardVO;
import com.company.domain.Criteria;
import com.company.domain.FileAttach;
import com.company.domain.PageVO;
import com.company.service.BoardService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board/*")
public class BoardController {

	@Autowired
	private BoardService service;

	// 게시글 작성 폼 보여주기
	@GetMapping("/register")
	public void register() {
		log.info("register form 요청");
	}

	// 게시글 작성
	@PostMapping("/register")
	public String registerPost(BoardVO board, RedirectAttributes rttr) {
		log.info("게시글 등록 : " + board);

		// 파일첨부 확인
		if (board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info("" + attach));
		}
		log.info("게시글 번호 : " + board.getBno());
		if (service.regist(board)) {
			rttr.addFlashAttribute("result", board.getBno());
			return "redirect:list";
		}
		return "register";
	}

	// 게시글 목록 보기
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		log.info("게시글 보기");
		List<BoardVO> list = service.getList(cri);
		int total = service.getTotalCnt(cri);
		log.info("전체 게시물 수 : " + total);
		model.addAttribute("list", list);
		PageVO pageVo = new PageVO(cri, total);
		log.info("pageVO : " + pageVo);
		model.addAttribute("pageVO", pageVo);
	}

	// 특정 게시물 보여주기
	// /board/read?bno=7
	// /board/modify?bno=7 =>modify.jsp
	@GetMapping({ "/read", "/modify" })
	public void get(int bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("게시물 보기" + bno);
		log.info("read : Criteria : " + cri);
		BoardVO board = service.getRow(bno);
		model.addAttribute("board", board);
	}

	// 특정 게시물 삭제하기
	@PostMapping("/remove")
	public String remove(int bno, Criteria cri, RedirectAttributes rttr) {
		log.info("게시물 삭제" + bno);
		
		//게시물 번호에 해당하는 파일 첨부 파일 삭제(서버, 데이터베이스도 삭제)
		
		//서버 폴더 안 파일 삭제하기
		//①bno에 해당하는 첨부물 목록 알아내기
		List<FileAttach> attachList =service.getAttachList(bno);
		// 성공하면 리스트 보여주기
		if(service.remove(bno)) {//②데이터베이스 삭제(게시물, 첨부물)
			//③파일 삭제
			deleteFiles(attachList);
			// session 객체에 담에서 이동한다 But 강한 휘발성
			rttr.addFlashAttribute("result", "success");
			
		}
		
		// 주소줄에 따라간다.
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		// 나누어 보내는 이유 : redirect 방식 - 객체를 보낼 수 없다.
		// addFlashAttribute : session 객체를 이용하는 방식
		// addAttribute : 주소줄에 따라가는 방식

		return "redirect:list";
	}
	
	private void deleteFiles(List<FileAttach> attachList) {
		log.info("첨부물 삭제 : "+attachList);
		
		if(attachList == null || attachList.size() <=0) {
			return;
		}
		for(FileAttach attach:attachList) {
			Path path = Paths.get("g:\\upload\\", attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
			
			//일반 파일, 이미지 원본 파일 삭제
			try {
				Files.deleteIfExists(path);
				
				if(Files.probeContentType(path).startsWith("image")) {
					Path thumb = Paths.get("g:\\upload\\", attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					Files.delete(thumb);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 데이터 유지 O : forward 방식
	// 데이터 유지 X : redirect 방식

	// 특정 게시물 수정
	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
		log.info("게시물 수정 " + board); // 동일한 경로의 post로 이동시 : get에서 받은 데이터 유지 cri != null
		log.info("modifyPost : Criteria : " + cri);

		// 파일첨부 확인
		if (board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info("" + attach));
		}

		service.modify(board);
		rttr.addFlashAttribute("result", "success");
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:list";
	}

	// 첨부물 가져오기
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<FileAttach>> getAttachList(int bno) {
		log.info("첨부물 가져오기 : " + bno);
		return new ResponseEntity<List<FileAttach>>(service.getAttachList(bno), HttpStatus.OK);
	}
}
