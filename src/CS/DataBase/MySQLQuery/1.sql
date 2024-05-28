
# MySQL 주석 방법

/* 주석 방법1 */
# 주석 방법2

show DATABASES; # 현재 존재하는 데이터베이스를 보여준다.

CREATE database company; # CREATE DATABASE 내가 만들고 싶은 데이터베이스 이름;

SHOW DATABASES; # 이제 내가 만든 데이터베이스인 company가 보인다.

SELECT database(); # 내가 현재 선택한 데이터베이스를 보여준다. 선택한 DB가 없으면 NULL

USE company; # 데이터베이스 선택


# DEPARTMENT Table 만들기
create table DEPARTMENT(
       id INT primary key,
       name VARCHAR(20) NOT NULL unique,
       leader_id INT
);

# EMPLOYEE Table 만들기
create table EMPLOYEE(
         id INT primary key,
         name VARCHAR(30) NOT NULL ,
         birth_date DATE,
         sex CHAR(1)  CHECK(sex in('M','F')),
         salary INT DEFAULT 50000000,
         dept_id INT,
         foreign key (dept_id) references DEPARTMENT(id)
             on delete SET null on update CASCADE,
         check (salary >= 50000000)
);

# PROJECT Table 만들기
create table PROJECT(
        id INT primary key,
        name VARCHAR(30) NOT NULL unique,
        leader_id INT,
        start_date DATE,
        end_date DATE,
        CONSTRAINT start_date_end_date check(start_date < end_date),
        foreign key (leader_id) references EMPLOYEE(id)
            on delete SET null on update CASCADE
);

#WORKS_ON Table 만들기
create table WORKS_ON(
         empl_id INT,
         proj_id INT,
         primary key (empl_id, proj_id),
         foreign key (empl_id) references EMPLOYEE(id)
             on delete CASCADE on update CASCADE,
         foreign key (proj_id) references PROJECT(id)
             on delete CASCADE on update CASCADE
);

#ALTER TABLE 사용해서 DEPARTMENT leader_id에 EMPLOYEE reference 추가하기
ALTER TABLE DEPARTMENT ADD foreign key(leader_id)
    references EMPLOYEE(id)
    on update cascade
    on delete SET NULL;