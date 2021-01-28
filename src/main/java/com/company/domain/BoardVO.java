package com.company.domain;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardVO {
	private int bno;
	private String title;
	private String writer;
	private String content;
	private Date regDate;
	private Date updateDate;
	private int replyCnt;
	
	//파일 첨부 목록
	private List<FileAttach> attachList;
}
