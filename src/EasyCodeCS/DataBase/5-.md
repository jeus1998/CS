
# SELECT 데이터 조회하기 (기초)

### SELECT statement1

- ID가 9인 임직원의 이름과 직군을 알고 싶다 
```sql
SELECT name, position 
FROM EMPLOYEE
WHERE id = 9;
```
![36.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F36.JPG)
- selection condition: 검색 조건
  - WHERE id = 9;
- projection attributes: 관심 attribute(s)
  - SELECT name, position

### SELECT statement 정리

```sql
SELECT attribute(s)
FROM table(s)
[WHERE condition(s)];
```

### SELECT statement2

- project 2002를 리딩(leading)하고 있는 임직원의 ID와 이름과 직군을 알고 싶다
```sql
SELECT e.id, e.name, e.position
FROM employee e, project p
WHERE p.id = 2002 and e.id = p.leader_id;
```
- selection condition: p.id = 2002
- join condition: p.leader_id = e.id - 두 테이블을 연결 
- projection condition: e.id, e.name, e.position
- 두 테이블에 동일한 attribute를 query에 사용한다면 테이블을 명시해 줘야 한다.

### AS

- AS는 테이블이나 attribute에 별칭(alias)을 붙일 때 사용한다
- AS는 생략 가능하다
- projection condition에도 사용 가능 검색 결과가 별칭으로 나온다.

### DISTINCT 사용하기

- 디자이너들이 참여하고 있는 프로젝트들의 ID와 이름을 알고 싶다

```sql
SELECT distinct p.id, p.name
FROM employee e, works_on w, project p
WHERE e.position = 'DSGN' and w.empl_id = e.id and w.proj_id = p.id;
```
- DISTINCT는 select 결과에서 중복되는 tuples를 제외하고 싶을 때 사용한다.

### LIKE 사용하기

- 이름이 N으로 시작하거나 N으로 끝나는 임직원들의 이름을 알고 싶다.
```sql
SELECT name
FROM EMPLOYEE e
WHERE e.name LIKE 'N%' or e.name LIKE '%N';
```

- 이름에 NG가 들어가는 임직원들의 이름을 알고 싶다
```sql
SELECT name
FROM EMPLOYEE e
WHERE e.name LIKE '%NG%';
```

- 이름에 J로 시작하는, 총 네 글자의 이름을 가지는 임직원들의 이름을 알고 싶다
```sql
SELECT name
FROM EMPLOYEE e
WHERE e.name LIKE 'J___';
```

### escape 문자와 함께 LIKE 사용하기

- %로 시작하거나 _로 끝나는 프로젝트 이름을 찾고 싶다면?
- \ 사용(백 슬래시)
```sql
SELECT name
FROM project
WHERE name LIKE '\%%' or name LIKE '%\_';
```

### LIKE 정리

![37.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F37.JPG)

### *(asterisk) 사용하기

- ID가 9인 임직원의 모든 attributes를 알고 싶다
```sql
SELECT *
FROM EMPLOYEE
WHERE id = 9;
```

### SELECT 주의사항

- SELECT로 조회할 때 조건들을 포함해서 조회를 한다면 이 조건들과 관련된 attributes에 index가 걸려있어야 한다.
- 그렇지 않다면 데이터가 많아질수록 조회 속도가 느려진다.
- ex) SELECT * FROM employee WHERE position = 'DEV_BACK';