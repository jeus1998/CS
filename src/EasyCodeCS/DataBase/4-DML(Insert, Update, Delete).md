# DML(Insert, Update, Delete)

## 데이터 삽입(Insert)

![35.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F35.JPG)

```sql
INSERT INTO employee
    VALUES (1, 'MESSI', '1987-02-01', 'M', 'DEV_BACK', 100000000, null);
```
- attribute 순서대로 값을 넣어야 한다.
- 모든 attribute에 대응하는 값을 넣어줘야 한다.

```sql
INSERT INTO employee
    VALUES (2, 'JANE', '1996-05-05', 'F', 'DSGN', 90000000, null);
```

```sql
INSERT INTO employee(name, birth_date, sex, position, id)
    VALUES ('JENNY', '2000-10-12', 'F', 'DEV_BACK', 3);
``` 
- salary: default 값 50000000 (default constraint)
- dept_id: null 

### INSERT statement 

- INSERT INTO table_name VALUES(all values);
- INSERT INTO table_name (attributes list) VALUES(attributes list 순서와 동일하게 values);
- INSERT INTO table_name VALUES (..., ...), (..., ..), (..., ..);

```sql
insert into employee values
(4, 'BROWN', '1996-03-13', 'M', 'CEO', 120000000, null),
(5, 'DINGYO', '1990-11-05', 'M', 'CTO', 120000000, null),
(6, 'JULIA', '1986-12-11', 'F', 'CFO', 120000000, null),
(7, 'MINA', '1993-06-17', 'F', 'DSGN', 80000000, null),
(8, 'JOHN', '1999-10-22', 'M', 'DEV_FRONT', 65000000, null),
(9, 'HENRY', '1982-05-20', 'M', 'HR', 82000000, null),
(10, 'NICOLE', '1991-03-26', 'F', 'DEV_FRONT', 90000000, null),
(11, 'SUZANNE', '1993-03-23', 'F', 'PO', 75000000, null),
(12, 'CURRY', '1998-01-15', 'M', 'PLN', 85000000, null),
(13, 'JISUNG', '1989-07-07', 'M', 'PO', 90000000, null),
(14, 'SAM', '1992-08-04', 'M', 'DEV_INFRA', 70000000, null);
```
### DEPARTMENT DATA INSERT

```sql
insert into department values
(1001, 'headquarter', 4),
(1002, 'HR', 6),
(1003, 'development', 1),
(1004, 'design', 3),
(1005, 'product', 13);
```

### PROJECT DATA INSERT

```sql
insert into project values
(2001, '쿠폰 구매/선물 서비스 개발', 13, '2022-03-10', '2022-07-09'),
(2002, '확장성 있게 백엔드 리팩토링', 13, '2022-01-23', '2022-03-23'),
(2003, '홈페이지 UI 개선', 11, '2022-05-09', '2022-06-11');
```

### WORKS_ON DATA INSERT

```sql
insert into works_on values 
    (5, 2001),
    (13, 2001),
    (1, 2001),
    (8, 2001),
    (7, 2003),
    (2, 2003),
    (12, 2003);
```

## 데이터 수정(Update)

### UPDATE statement1

- employee ID가 1인 Messi는 개발(development)팀 소속이다
- 개발팀 ID는 1003이다
- Messi의 소속팀 정보를 업데이트 해주자
```sql
UPDATE employee SET dept_id = 1003 WHERE id = 1;
```

### UPDATE statement2

- 개발팀 연봉을 두 배로 인상하고 싶다
- 개발팀 ID는 1003이다.

```sql
UPDATE employee SET salary = salary*2 WHERE dept_id = 1003;    
```

### UPDATE statement3

- 프로젝트 ID 2003에 참여한 임직원의 연봉을 두 배로 인상하고 싶다

```sql
UPDATE employee, works_on SET salary = salary * 2 
WHERE id = empl_id and proj_id = 2003;
```

### UPDATE statement4

- 회사의 모든 구성원의 연봉을 두 배로 올리자 
```sql
UPDATE employee SET salary = salary * 2;
```

### UPDATE statement 정리

```sql
UPDATE table_name(s) 
SET attribute = value[, attribute=value, ..] 
[WHERE condition(s)];
```

## 데이터 삭제(Delete)

### DELETE statement1

- John이 퇴사를 하게 되면서 employee 테이블에서 John 데이터를 삭제해야 한다.
- John의 employee ID는 8이다.
- 현재 John은 project 2001에 참여하고 있었다.

```sql
DELETE FROM employee where id = 8;
```
- works_on table에 있는 John 관련 정보는 cascade로 묶여 있어서 같이 삭제된다.

### DELETE statement2

- Jane이 휴직을 떠나게 되면서 현재 진행 중인 프로젝트에서 중도하차하게 됐다.
- Jane의 ID = 2

```sql
DELETE FROM works_on where empl_id = 2;
```

### DELETE statement3

- 현재 Dingyo가 두 개의 프로젝트에 참여하고 있었는데
- 프로젝트 2001에 선택과 집중을 하기로 하고 프로젝트 2002에서는 빠지기로 했다.
- Dinayo의 ID = 5

```sql
DELETE FROM WORKS_ON WHERE empl_id = 5 and proj_id = 2002;
```

- 만약 Dingyo가 N개의 프로젝트에 참여하고 있는데 프로젝트 2001을 제외하고 모든 프로젝트에서 빠지기로 했다면?

```sql
DELETE FROM WORKS_ON WHERE  empl_id = 5 and proj_id <> 2001;

DELETE FROM WORKS_ON WHERE  empl_id = 5 and proj_id != 2001;
```
- ``` <>, != 같은 표현 ```

### DELETE statement4

- 회사에 큰 문제가 생겨서 진행중인 모든 프로젝트들이 중단됐다.

```sql
DELETE FROM PROJECT;
```
### DELETE statement 정리

```sql
DELETE  FROM table_name
[WHERE condition(s)];
```
