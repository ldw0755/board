--파일 첨부 테이블 생성
create table spring_attach(
	uuid varchar2(100) not null,
	uploadPath varchar2(200) not null,
	fileName varchar2(100) not null,
	fileType char(1) not null,
	bno number(10)
);
alter table spring_attach drop constraint pk_attach;
alter table spring_attach add constraint pk_attach primary key(uuid);
alter table spring_attach
add constraint fk_board_attach foreign key(bno) references spring_board(bno);

select * from spring_attach where bno=1602;

select * from spring_attach where bno=1603;

select * from user_tables;

select TABLE_NAME, COLUMN_ID, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, NULLABLE
from ALL_TAB_COLUMNS
where TABLE_NAME='SPRING_BOARD' ORDER BY COLUMN_ID ASC;

select * from member;