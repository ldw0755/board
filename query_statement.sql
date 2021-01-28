--페이지 나누기 : rownum
select rownum, bno, title from spring_board where rownum<=10;

--가장 최신 글 10개 가져오기
select rownum, bno, title
from (select bno, title from spring_board order by bno desc)
where rownum <=10;

select /*+INDEX_DESC(spring_board pk_spring_board)*/ rownum, bno, title
from spring_board
where rownum <=10;

--1page => 가장 최신글 10개
select rn, bno, title
from(select rownum rn, bno, title
	 from (select bno, title from spring_board order by bno desc)
	 where rownum <=10)
where rn>0;

select rn, bno, title
from (select /*+INDEX_DESC(spring_board pk_spring_board)*/ rownum rn, bno, title
	  from spring_board where rownum <=10)
where rn>0;

--2page => 그 다음 최신글 10개
select rn, bno, title
from(select rownum rn, bno, title
	 from (select bno, title from spring_board order by bno desc)
	 where rownum <=20)
where rn>10;

select *
from (select /*+INDEX_DESC(spring_board pk_spring_board)*/ 
	  rownum rn, bno, title, writer, regdate, updatedate
	  from spring_board where rownum <=20)
where rn>10;

--page 번호가 넘어오는 경우
--1(n) => 10(10n), 0((n-1)*10)	//10 : 현제 페이지에 보여줄 게시물 수
--2 => 20(10n), 10((n-1)*10)
select *
from (select /*+INDEX_DESC(spring_board pk_spring_board)*/ 
	  rownum rn, bno, title, writer, regdate, updatedate
	  from spring_board where rownum <=?)
where rn>?

--dummy data
insert into spring_board(bno, title, content, writer)
(select seq_board.nextval, title, content, writer from spring_board);

select count(*) from spring_board;
select bno, title, writer from spring_board;

--검색 : 제목
select *
from (select /*+INDEX_DESC(spring_board pk_spring_board)*/ 
	  rownum rn, bno, title, writer, regdate, updatedate
	  from spring_board where title like '%수정%' and rownum <=10)
where rn>0

--검색 : 제목 + 내용
select *
from (select /*+INDEX_DESC(spring_board pk_spring_board)*/ 
	  rownum rn, bno, title, writer, regdate, updatedate
	  from spring_board where (title like '%수정%' or content like '%context%') and rownum <=10)
where rn>0

--검색 : 제목 + 작성자
select *
from (select /*+INDEX_DESC(spring_board pk_spring_board)*/ 
	  rownum rn, bno, title, writer, regdate, updatedate
	  from spring_board where (title like '%수정%' or writer like '%context%') and rownum <=10)
where rn>0

select * from spring_reply where rno=8;