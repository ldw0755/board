package com.company.except;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice	//AOP : main 관심사가 아니라 sub 관심사에 대한 내용 실행할 때
@Slf4j
public class CommonException {

	@ExceptionHandler(Exception.class)
	public String except(Exception e, Model model) {
		log.error("Exception....."+e);
		model.addAttribute("error", e.getMessage());
		return "except/error";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String notFound(NoHandlerFoundException e) {
		log.error("Exception");
		return "except/view404";
	}
}
