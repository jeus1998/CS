
# SQL SELECT - 데이터 조회하기 (NULL, three-valued logic)

## Null

### SQL에서 NULL 의미
- unknown
  - 어떤 사람의 생일은 당연히 존재하지만 기입 ❌
- unavailable or withheld
  - 어떤 사람의 생일은 당연히 존재하지만 공개 ❌
- not applicable
  - 집 전화번호 attribute가 있지만 집 전화가 ❌ 
  - 해당사항 ❌ 

### NULL을 찾는 비교 연산자는?

```sql
SELECT id 
FROM EMPLOYEE
WHERE birth_date = NULL;
```
- 이렇게 SELECT 해서 결과가 없으면 NULL 값을 가지는 튜플이 없다고 생각하겠지만 아니다.
- 올바른 연산자 ➡️ IS

```sql
SELECT id 
FROM EMPLOYEE
WHERE birth_date IS NULL;
```

### Null과 Three-Valued Logic

```sql
SELECT *
FROM EMPLOYEE
WHERE birth_date = '1990-03-09';
```
- SQL에서 NULL과 비교 연산을 하게 되면 그 결과는 UNKNOWN 이다.
  - NULL = '1990-03-09'
- UNKNOWN은 의미는 TRUE OR FALSE 
- three-valued logic: 비교/논리 연산의 결과로 TRUE, FALSE. UNKNOWN을 가진다.

### NULL 비교 연산 결과

![38.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F38.JPG)

![39.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F39.JPG)

### WHERE절의 condition(s)

- where절에 있는 condition(s)의 결과는 TRUE인 tuple(s)만 선택 된다
- 즉 결과가 FALSE거나 UNKNOWN이면 tuple은 선택되지 않는다.

### NOT IN 사용 시 주의사항

- v NOT IN (v1,v2,v3)는 아래와 같은 의미이다.
- v != v1 AND v != v2 AND v != v3
- 만약 v1,v2,v3 중에 하나가 NULL 이라면?

NOT IN 예제
- 3 not in (1,2,4)
  - TRUE
- 3 not in (1,2,3)
  - FALSE
- 3 not in (1,3,NULL)
  - FALSE
- 3 not in (1,2,NULL)
  - UNKNOWN

```sql
SELECT D.id, D.name
FROM department AS D
WHERE D.id NOT IN ( 
             SELECT E.dept_id
             FROM employee E
             WHERE E.birth_date >= '2000-01-01'    
  );
```

- IS NOT NULL 사용 
```sql
SELECT D.id, D.name
FROM department AS D
WHERE D.id NOT IN ( 
             SELECT E.dept_id
             FROM employee E
             WHERE E.birth_date >= '2000-01-01' AND E.birth_date IS NOT NULL
  );
```

- NOT EXISTS 사용 
```sql
SELECT D.id, D.name
FROM department AS D
WHERE D.id NOT EXISTS ( 
             SELECT E.dept_id
             FROM employee E
             WHERE E.birth_date >= '2000-01-01'
  );
```

- NOT NULL constraint 넣기 





