# stored procedure

### stored procedure?

- 사용자가 정의한 프로시저 
- RDBMS에 저장되고 사용되는 프로시저 
- 구체적인 하나의 태스크(task)를 수행한다

### stored procedure 예제1

- 두 정수의 곱셈 결과를 가져오는 프로시저를 정의하자 
```sql
delimiter $$
CREATE PROCEDURE product(IN a int, In b int, OUT result int)
BEGIN
    SET result = a * b;
END
$$
delimiter ;    
```
- ``IN a int, In b int`` input 파라미터
  - IN을 생략해도 input 파라미터로 동작한다. 
  - ``a int, b int, OUT result int``

프로시저 사용하기
```sql
call product (5, 7, @result);
select @result;
```

### stored procedure 예제2

- 두 정수를 맞바꾸는 프로시저를 작성하자 (swap)
```sql
delimiter $$
CREATE PROCEDURE swap(INOUT a int, INOUT b int)
BEGIN
    SET @temp = a;
    SET a = b;
    SET b = @temp;    
END
$$
delimiter ;
```

프로시저 사용하기
```sql
set @a = 5, @b = 7;
call swap(@a, @b);
select @a, @b; 
```

### stored procedure 예제3

- 각 부서별 평균 연봉을 가져오는 프로시저를 작성하자
```sql
delimiter $$
CREATE PROCEDURE get_dept_avg_salary()
BEGIN     
    select dept_id, avg(salary)
    from employee
    group by dept_id;
END    
$$
delimiter ;   
```

프로시저 사용하기
```sql
call get_dept_avg_salary()
```

### stored procedure 예제4
- 사용자가 프로필 닉네임을 바꾸면 
- 이전 닉네임을 로그에 저장하고 새 닉네임으로 업데이트하는 프로시저를 작성하자 

```sql
delimiter &&
CREATE PROCEDURE change_nickname(user_id INT, new_nick varchar(30))
BEGIN
    insert into nickname_logs(
        select id, nickname, now() 
        from users 
        where id = user_id                         
    );
    update users set nickname = new_nick 
    where id = user_id;
END    
$$
delimiter ;
```

프로시저 실행
```sql
call change_nickname(1, 'ZIDANE');
```

### stored procedure 정리
- 조건문을 통해 분기처리를 하거나 
- 반복문을 수행하거나
- 에러를 핸들링하거나 에러를 일으키는 등의 다양한 로직을 정의할 수 있다

### stored procedure VS stored function

![53.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F53.JPG)


다른 함수/프로시저 호출 가능 여부 
- 저장 함수(stored function)
  - stored function 내에서 다른 stored function 호출 가능⭕️
  - stored function 내에서 다른 stored procedure 호출 불가능❌
- 저장 프로시저(stored procedure)
  - stored procedure 내에서 다른 stored function 호출 가능⭕️
  - stored function 내에서 다른 stored procedure 호출 가능⭕️

ResultSet(table) 반환 가능 여부
- 저장 함수(stored function)
  - stored function은 단일 값만 반환할 수 있다. 테이블 형태의 결과 반환❌
  - 함수 내부에서 커서를 사용하여 간접적으로 반환은 가능
  - 직접적인 테이블 형태의 반환 불가능❌
- 저장 프로시저(stored procedure)
  - 테이블 형태의 결과 반환 가능⭕️
  - SELECT 문을 실행하여 쿼리 결과를 클라이언트에게 반환할 수 있다. 
  - 여러 개의 ResultSet을 반환할 수도 있다. 

Precompiled Execution Plan 사용 여부
- 저장 함수(stored function)
  - MySQL은 저장 함수를 컴파일하고 최적화된 실행 계획을 생성
  - 함수가 호출될 때마다 동일한 최적화된 실행 계획을 사용할 수 있도록 한다.
- 저장 프로시저(stored procedure)
  - 저장 프로시저도 저장 함수와 마찬가지로 컴파일되고 최적화된 실행 계획을 생성
  - 프로시저가 호출될 때마다 동일한 최적화된 실행 계획을 사용할 수 있도록 한다.

예외 처리 (try-catch) 가능 여부
- 저장 함수(stored function)
  - MySQL에서는 저장 함수 내에서 예외 처리를 위한 DECLARE ... HANDLER를 사용할 수 있다.
  - 저장 함수 내에서 커서(Cursor)와 핸들러(Handler)를 사용하는 것은 제한적
- 저장 프로시저(stored procedure)
  - 저장 프로시저에서는 예외 처리를 위한 DECLARE ... HANDLER를 사용할 수 있다.
  - 프로시저는 저장 함수보다 더 유연하게 예외를 처리할 수 있으며, 
    다양한 오류 상황에 대해 구체적으로 대응할 수 있다.

요약
![54.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F54.JPG)