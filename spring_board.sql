create table spring_board(
	bno number(10,0),
	title varchar2(200) not null,
	content varchar2(2000) not null,
	writer varchar2(50) not null,
	regdate date default sysdate,
	updatedate date default sysdate
);

select * from spring_board;

alter table spring_board add constraint pk_spring_board primary key(bno);

create sequence seq_board;

--댓글 테이블
create table spring_reply(
	rno number(10,0) constraint pk_reply primary key,	--댓글 글번호
	bno number(10,0) not null,	--원본글 글번호
	reply varchar2(1000) not null, --댓글
	replyer varchar2(50) not null, --댓글 작성자
	replyDate date default sysdate,	--댓글 작성일
	updateDate date default sysdate,	--댓글 수정일
	constraint fk_reply_board foreign key(bno) references spring_board(bno)	--foreign key 설정(테이블 연결)
);

create sequence seq_reply;

--인덱스 생성
create index index_reply on spring_reply(bno desc, rno asc);

select * from spring_reply;

--댓글 개수를 저장할 컬럼 생성(spring_board)
alter table spring_board add(replycnt number default 0);

select * from spring_board order by bno desc;

--이미 들어간 댓글 갯수 삽입
update spring_board
set replycnt=(select count(rno) from spring_reply where spring_board.bno=spring_reply.bno);