# Stored Function & Stored Procedure

### Stored Procedure

서버측에 쿼리를 등록하고 클라이언트에서 저장된 쿼리 명을 불러서 사용
- 보안성을 높일 수 있다
- 다양한 처리 가능
- 네트워크 부하를 줄일 수 있다
- 리턴값이 있을 수도 있고 없을 수도 있다. 
- 파라미터(매개변수) 전달 가능 

### Stored Procedure 예시

문자를 매개변수로 넘겨주면 해당 문자를 포함하는 고객들 보여주기  
```sql
delimiter $$
CREATE PROCEDURE getCustomers(IN name VARCHAR(50))
BEGIN
	SELECT * 
    FROM CUSTOMER WHERE 
    CUSTOMERNAME LIKE CONCAT('%', name, '%');
END
$$
delimiter ;
```

사용
```sql
CALL getCustomers('님');
```

프로시저 삭제
```sql
DROP PROCEDURE getCustomers;
```

### Stored Function 예시

a + b 결과를 반환하는 Stored Fuction 만들기 
```sql
delimiter $$
CREATE FUNCTION PLUS(a INT, b INT)
RETURNS INT
NO SQL
BEGIN
	RETURN a + b;
END
$$
delimiter ;
```
사용
```sql
SELECT PLUS(3,5) AS RESULT;
```

똑같은 기능 변수 선언을 통한 RETURN
```sql
delimiter $$
CREATE FUNCTION PLUS2(a INT, b INT)
RETURNS INT
NO SQL
BEGIN
	DECLARE c INT;
    SET c = a + b;
	RETURN c;
END
$$
delimiter ;
```

사용
```sql
SELECT PLUS2(3,5) AS RESULT;
```

### MySQL 기본 Stored Function, Stored Procedure

YYYY-MM-DD
```sql
SELECT CURDATE();
```

YYYY-MM-DD HH:MM:SS
```sql
SELECT NOW();
```

Stored Function
```TEXT
ABS(x): x의 절대값을 반환
ROUND(x, d): x를 d 소수점 자릿수로 반올림
CONCAT(str1, str2, ...): 문자열을 연결
UPPER(str): 문자열을 대문자로 변환
LOWER(str): 문자열을 소문자로 변환
SUBSTRING_INDEX(str, delim, count): 문자열 str에서 delim으로 지정된 구분자를 기준
```

Stored Procedure
```text
DELIMITER: 다수의 SQL 문을 구분하는 데 사용
CREATE PROCEDURE: 새 프로시저를 생성
DROP PROCEDURE: 기존 프로시저를 삭제
CALL: 저장된 프로시저를 호출
IF...THEN...ELSE: 조건에 따라 프로시저의 실행 흐름을 제어
WHILE...DO: 조건이 충족될 때까지 반복하여 실행
```