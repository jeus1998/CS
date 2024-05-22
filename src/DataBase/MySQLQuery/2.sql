
# EMPLOYEE Table 만들기 (position 누락 )
create table EMPLOYEE (
      id INT primary key,
      name VARCHAR(30) NOT NULL ,
      birth_date DATE,
      sex CHAR(1)  CHECK(sex in('M','F')),
      position varchar(10),
      salary INT DEFAULT 50000000,
      dept_id INT,
      foreign key (dept_id) references DEPARTMENT(id)
          on delete SET null on update CASCADE,
      check (salary >= 50000000)
);

INSERT INTO employee
VALUES(1, 'MEESI', '1987-02-01', 'M', 'DEV_BACK', 100000000, null);

INSERT INTO employee
VALUES(2, 'JANE', '1996-12-12', 'F', 'DSGN', 90000000, null);

# 오류가 발생했을 때 테이블에 걸려있는 제약조건을 확인하는 용도
SHOW CREATE TABLE employee;

insert INTO employee (name, birth_date, sex, position, id)
values ('JENNY', '2000-10-12', 'f', 'DEV_BACK', 3);

# 데이터 조회하기
select * from employee;

# 여러개의 튜플 데이터 넣기
insert into employee values
(4, 'BROWN', '1996-03-13', 'M', 'CEO', 120000000, null),
(5, 'DINGYO', '1990-11-05', 'M', 'CTO', 120000000, null),
(6, 'JULIA', '1986-12-11', 'F', 'CFO', 120000000, NULL),
(7, 'MINA', '1993-06-17', 'F', 'DSGN', 80000000, NULL),
(8, 'JOHN', '1999-10-22', 'M', 'DEV_FRONT', 65000000, NULL),
(9, 'HENRY', '1982-05-20', 'M', 'HR', 82000000, NULL),
(10, 'NICOLE', '1991-03-26', 'F', 'DEV_FRONT', 90000000, NULL),
(11, 'SUZANNE', '1993-03-23', 'F', 'PO', 75000000, NULL),
(12, 'CURRY', '1998-01-15', 'M', 'PLN', 85000000, NULL),
(13, 'JISUNG', '1989-07-07', 'M', 'PO', 90000000, NULL),
(14, 'SAM', '1992-08-04', 'M', 'DEV_INFRA', 70000000, NULL);


# department 데이터 넣기
insert into department values
(1001, 'headquarter', 4),
(1002, 'HR', 6),
(1003, 'development', 1),
(1004, 'design', 3),
(1005, 'product', 13);

# project 데이터 넣기
insert into project values
(2001, '쿠폰구매/선물 서비스 개발', 13, '2022-03-10', '2022-07-09'),
(2002, '확정성 있게 백엔드 리팩토링', 13, '2022-01-23', '2022-03-23'),
(2003, '홈페이지 UI 개선', 11, '2022-05-09', '2022-06-11');

# works_on 데이터 넣기
insert into works_on values
(5, 2001),
(13, 2001),
(1, 2001),
(8, 2001),
(7, 2003),
(2, 2003),
(12, 2003);

# messi update
update employee set dept_id = 1003 where id = 1;

# update check
select * from employee where id = 1;

# 나머지 update
update employee set dept_id = 1004 where id = 2;
update employee set dept_id = 1003 where id = 3;
update employee set dept_id = 1005 where id = 13;
update employee set dept_id = 1003 where id = 14;
update employee set dept_id = 1003 where id = 8;
update employee set dept_id = 1003 where id = 10;

# 개발팀 부서 연봉 2배 인상 dept_id = 1003
update employee set salary = salary * 2
where dept_id = 1003;

# 프로젝트 ID 2003에 참여한 임직원의 연봉을 두 배로 인상하고 싶다
update employee , works_on
set salary = salary * 2
where id = employee.id = works_on.empl_id and works_on.proj_id = 2003;

/* safe mode 에러 발생
21:48:07	update employee , works_on set employee.salary = employee.salary * 2  where id = employee.id = works_on.empl_id and works_on.proj_id = 2003
Error Code: 1175.
You are using safe update mode and you tried to update a table without a WHERE that uses a KEY column.
To disable safe mode, toggle the option in Preferences -> SQL Editor and reconnect.	0.015 sec

update employee , works_on
set employee.salary = employee.salary * 2
where works_on.empl_id = employee.id and works_on.proj_id = 2003;

-> safe mode 해제

*/
#모든 회사원의 연봉을 두 배로 인상하자
update employee set salary = salary * 2;

# john 삭제 works_on에 있는 john 정보는 cascade 조건으로 같이 삭제
delete from employee where id = 8;

# jane 프로젝트 하차 시키기
delete from works_on where empl_id = 2;

# works_on에 dingyo project 2002가 없어서 추가하기
insert into works_on values(5, 2002);

# Dingyo 2002 project 빠지기
delete from works_on
where empl_id = 5 and proj_id = 2002;

# Dingyo 2001 project 제외 다 빠지기
delete from works_on
where empl_id = 5 and proj_id <> 2001; #  <> , != 모두 똑같다

# 회사에 큰 문제가 생겨서 진행중인 모든 프로젝트들이 중단됐다.
delete from project;
delete from works_on;