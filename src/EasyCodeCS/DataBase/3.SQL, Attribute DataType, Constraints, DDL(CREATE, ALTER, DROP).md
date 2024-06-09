
# SQL, Attribute DataType, Constraints, DDL(CREATE, ALTER, DROP)

## SQL 기본 개념 

### SQL
- Structured Query Language
- 현업에서 쓰이는 relational DBMS 표준 언어
- 종합적인 database 언어: DDL + DML + VDL

### SQL 주요 용어

![19.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F19.JPG)

### SQL에서 relation?

- multiset of tuples @ SQL
- 중복된 tuple을 허용한다

### SQL & RDBMS

- SQL은 RDBMS 표준 언어이지만 실제 구현에 강제가 없다
- 그래서 RDBMS 마다 제공하는 SQL 스펙이 조금씩 다르다.

## IT 회사 관련 RDB 만들기 

- 예제를 통해서 SQL로 DB 정의 하기 
- 부서, 사원, 프로젝트 관련 정보들을 저장할 수 있는 관계형 데이터베이스를 만들자
- 사용할 RDBMS MySQL(InnoDB)

### DB 만들기

현재 존재하는 데이터베이스 보기
```sql
SHOW DATABASES;
```
![20.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F20.JPG)

database 만들기
```sql
CREATE DATABASE company;
```

- show databases; ➡️ 내가 만든 company가 보인다.

내가 활성화(선택)한 데이터 베이스 보기 
```sql
SELECT database();
```
데이터베이스 사용하기(활성화)
```sql
USE company;
```

데이터베이스 삭제하기 
```sql
DROP DATABASE company;
```

✅ DATABASE VS SCHEMA
- MySQL에서는 DATABASE와 SCHEMA가 같은 뜻을 의미 
- CREATE DATABASE company = CREATE SCHEMA company
- 다른 RDBMS에서는 의미가 다르게 쓰임
- PostgreSQL에서는 SCHEMA가 DATABASE의 namespace를 의미 

## attribute data type

### 숫자
![22.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F22.JPG)
- RDBMS 마다 구현이 다르다.
- PostgreSQL: TINYINT, MEDIUMINT ❌ 
- 고정 소수점 방식 DECIMAL(precision, scale)
  - precision: 전체 숫자 크기 
  - scale: 소수점 자리 
  - ex) DECIMAL(5,2) ➡️ -999.99 ~ 999.99

### 문자열
![23.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F23.JPG)
- VARCHAR VS CHAR
  - PostgreSQL ➡️ VARCHAR 권장
  - MySQL
    - 핸드폰 번호 같이 고정 문자열은 CHAR
    - 가변 크기 문자열 데이터 VARCHAR
  - CHAR, VARCHAR 구현에 따라 다르다. 
  - 공간 복잡도 VS 시간 복잡도
- PostgreSQL: TEXT만 존재 

### 날짜와 시간
![24.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F24.JPG)

### 그 외
![25.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F25.JPG)

## Constraints

### Key constraints: PRIMARY KEY

- primary key: table의 tuple을 식별하기 위해 사용, 하나 이상의 attribute(s)로 구성
- primary key는 중복된 값을 가질 수 없으며, NULL도 값으로 가질 수 없다.
![26.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F26.JPG)

- primary key 선언하는 방법
![27.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F27.JPG)

### UNIQUE constraints

- UNIQUE로 지정된 attribute(s)는 중복된 값을 가질 수 없다.
- 단, NULL은 중복을 허용할 수도 있다.(RDBMS 마다 다름)
  - MySQL, PostgreSQL 허용 

- UNIQUE key 선언하는 방법
![28.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F28.JPG)

### NOT NULL constraints

- attribute가 NOT NULL로 지정되면 해당 attribute는 NULL을 값으로 가질 수 없다. 
- NOT NULL 선언 방법
![29.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F29.JPG)
- attribute 레벨만 가능 


### 테이블 만들기 (DEPARTMENT, EMPLOYEE)

![21.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F21.JPG)

DEPARTMENT 테이블 생성
```sql
create table DEPARTMENT(
	id  INT primary KEY,
    name varchar(20) NOT NULL unique,
    leader_id INT
);
```
EMPLOYEE 테이블 생성
```sql
create  table EMPLOYEE(
  id INT PRIMARY KEY,
  name VARCHAR(30) NOT NULL,
  birth_date DATE,
  sex CHAR(1)  CHECK(sex in ('M', 'F')),
  position VARCHAR(10),
  salary INT DEFAULT 50000000,
  dept_id INT,
  FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(id)
             on delete SET NULL on update CASCADE ,
  CHECK (salary >= 50000000)                  
);
```

### attribute DEFAULT

- attribute의 default 값을 정의할 때 사용 
- 새로운 tuple을 저장할 때 해당 attribute에 대한 값이 없다면 default 값으로 저장 
- 선언 방법
```sql
create  table Orders(
  ...
  menu varchar(15) DEFAULT '짜장면';
  ...                
);
```

### Check constraint

- attribute 값을 제한하고 싶을 때 사용 
- 선언 방법
![30.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F30.JPG)

### Referential integrity constraint : FOREIGN KEY

- attribute(s)가 다른 table의 primary key나 unique key를 참조할 때 사용 
- FK와 PK와 도메인이 같아야 하고 PK에 없는 values를 FK가 값으로 가질 수 없다.

![31.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F31.JPG)

선언 방법

![32.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F32.JPG)
- MySQL: set Default, No Action ❌

### constraint 이름 명시하기 
- 이름을 붙이면 어떤 constraint을 위반했는지 쉽게 파악할 수 있다.
- constraint를 삭제하고 싶을 때 해당 이름으로 삭제 가능 

![33.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F33.JPG)

- 테이블의 constraints 확인 방법 
```sql
show create table 테이블명;
```

### 테이블 만들기 (PROJECT, WORKS_ON)

PROJECT
```sql
CREATE TABLE  PROJECT(
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE ,
    leader_id INT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (leader_id) references EMPLOYEE(id)
                     on delete set null on update cascade,
    constraint start_end_date CHECK (start_date < end_date)                 
);
```

WORKS_ON
```sql
CREATE TABLE  WORKS_ON(
    empl_id INT,
    proj_id INT,
    FOREIGN KEY (empl_id) references EMPLOYEE (id) 
                 on delete cascade on update cascade,
    FOREIGN KEY (proj_id) references PROJECT(id)
                 on delete cascade on update cascade,
    PRIMARY KEY (empl_id, proj_id)
);
```

### ALTER TABLE로 SCHEMA 변경하기
- DEPARTMENT 테이블 생성 당시에 EMPLOYEE 테이블이 존재하지 않았다.
- leader_id에 constraint가 없는 상황 

```sql
ALTER TABLE DEPARTMENT ADD FOREIGN KEY (leader_id)
    references EMPLOYEE(id)
    on update CASCADE
    on delete SET NULL;
```

### ALTER TABLE 정리
- table schema를 변경하고 싶을 때 사용 
![34.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F34.JPG)

- 이미 서비스 중인 table의 schema를 변경하는 것이라면 
- 변경 작업 때문에 서비스의 백엔드에 영향이 없을지 검토한 후에 변경하는 것이 중요

### DROP TABLE
- table을 삭제할 때 사용 
- DROP TABLE table_name;

