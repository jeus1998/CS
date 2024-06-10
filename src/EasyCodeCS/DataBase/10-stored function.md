# stored function

### stored function 뜻
- 사용자가 정의한 함수 
- DBMS에 저장되고 사용되는 함수 
- SQL - (SELECT, INSERT, UPDATE, DELETE) statement에서 사용할 수 있다.

### stored function 예제1

- 임직원 ID를 열자리 정수로 랜덤하게 발급하고 싶다 
- ID의 맨 앞자리는 1로 고정이다

```sql
delimiter $$
CREATE FUNCTION id_generator()
RETURNS int
NO SQL
BEGIN
  RETURN (1000000000 + floor(rand() * 1000000000));
END
$$
delimiter ;
```
- ``delimiter $$``: 기본 구분자인 ``;`` 대신에 ``$$``를 사용하도록 변경 
  - 이렇게 하면 함수 본문 내에서 ``;``를 사용해도 함수 정의가 종료되지 않는다. 
- ``CREATE FUNCTION id_generator()``: id_generator라는 이름의 함수를 생성
- ``RETURNS int``: 정수를 반환
- ``NO SQL``: 해당 함수가 데이터베이스에 아무런 읽기나 쓰기 작업을 하지 않음을 나타낸다.
- ``BEGIN END`` 함수의 본문, 여기에 함수의 로직이 들어간다.
- ``RETURN (1000000000 + floor(rand() * 1000000000));``
  - rand(): 0과 1 사이의 난수를 생성 
  - rand() * 1000000000: 0과 10억 사이의 난수 생성
  - floor(...): 소수점을 버리고 정수만 취한다. 
  - return 10억 ~ 20억 랜덤한 정수 반환 
- ``delimiter ;``: 구분자 복원 

함수 사용 
```sql
INSERT INTO employee
    VALUES(id_generator(), 'JEHN', '1991-08-04', 'F', 'PO', 100000000, 1005);
```

데이터 확인
```sql
SELECT * 
FROM employee
where name = 'JEHN';
```
결과 
```text
id          name    birth_date sex position salary    dept_id
1244802315	JEHN	1991-08-04	F	PO	   100000000	1005
```

### stored function 예제2

- 부서의 ID를 파라미터로 받으면 해당 부서의 평균 연봉을 알려주는 함수를 작성하자 

```sql
delimiter &&
CREATE FUNCTION dept_avg_salary(d_id int)
RETURNS int
READS SQL DATA
BEGIN    
    DECLARE avg_sal int;
    select avg(salary) into avg_sal
                       from employee
                       where dept_id = d_id;
    RETURN avg_sal;
END    
$$    
delimiter ;
```
- ``d_id int``: 파라미터 
- ``READS SQL DATA``: 해당 함수는 데이터베이스에서 데이터를 읽는 작업을 수행함을 나타낸다.
- ``DECLARE``: 변수 선언 
- ``select avg(salary) into avg_sal``: employee 테이블에서 평균 연봉을 계산하고 avg_sal에 저장 

변수 선언 생략 버전 
```sql
delimiter &&
CREATE FUNCTION dept_avg_salary(d_id int)
RETURNS int
READS SQL DATA
BEGIN    
    select avg(salary) into @avg_sal
                       from employee
                       where dept_id = d_id;
    RETURN @avg_sal;
END    
$$    
delimiter ;
```

- 함수 사용하기 - 부서 정보와 부서 평균 연봉을 함께 가져오기
```sql
SELECT *, dept_avg_salary(id)
FROM department;
```

### stored function 예제3

- 졸업 요건 중 하나인 토익 800 이상을 충족했는지를 알려주는 함수를 작성하자
```sql
delimiter $$
CREATE FUNCTION toeic_pass_fail(toeic_score int)
RETURNS char(4)
NO SQL
BEGIN
    DECLARE pass_fail char(4);
    IF     toeic_score is null THEN SET pass_fail = 'fail';
    ELSEIF toeic_score < 800   THEN SET pass_fail = 'fail';
    ELSE                            SET pass_fail = 'pass';
    END IF;    
    RETURN pass_fail;    
END
$$
delimiter ;
```
- 변수 생략 버전
```sql
delimiter $$
CREATE FUNCTION toeic_pass_fail(toeic_score int)
RETURNS char(4)
NO SQL
BEGIN
    IF     toeic_score is null SET @pass_fail = 'fail';
    ELSEIF toeic_score < 800   SET @pass_fail = 'fail';
    ELSE                       SET @pass_fail = 'pass';
    END IF;    
    RETURN @pass_fail;    
END
$$
delimiter ;
```
- 함수 사용하기: 학생 정보와 함께 토익 점수 조건을 충족했는지 여부를 가져오기 
```sql
SELECT *, toeic_pass_fail(toeic)
FROM student;
```

### stored function 기능
- loop를 돌면서 반복적인 작업 수행 
- case 키워드를 사용해서 값에 따라 분기 처리 
- 에러를 핸들링하거나 에러를 일으키는 등의 다양한 동작을 정의할 수 있다.

### stored function 삭제
- DROP FUNCTION stored_function_name;

### 등록된 stored function 파악하기 

```sql
SHOW FUNCTION STATUS where DB = 'company';
```
- CREATE FUNCTION 당시 활성화된 DB에 저장 

```sql
CREATE FUNCTION  db_name.function_name()
```
- db_name에 해당하는 DB에 함수 저장 

```sql
SHOW DATABASES;
```
- DB 확인 

```sql
SHOW CREATE FUNCTION id_generator;
```
- 함수 구현 확인하기 

### stored function 언제 써야할까?

- util 함수로 쓰기에는 괜찮을 것 같다
- 비즈니스 로직을 stored function에 두는 것은 좋지 않을 것 같다


