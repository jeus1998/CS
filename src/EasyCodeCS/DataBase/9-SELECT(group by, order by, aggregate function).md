# SELECT(group by, order by, aggregate function)

## ORDER BY

- 조회 결과를 특정 attribute(s) 기준으로 정렬하여 가져오고 싶을 때 사용
- default 정렬 방식은 오름차순
- 오름차순 정렬은 ASC 
- 내림차순 정렬은 DESC 

### ORDER BY 예제

- 임직원들의 정보를 연봉 순서대로 정렬해서 알고싶다
```sql
SELECT *
FROM EMPLOYEE
ORDER BY SALARY;
```
- 임직원들의 정보를 연봉 순서대로 내림차순 정렬해서 알고싶다
```sql
SELECT *
FROM EMPLOYEE
ORDER BY SALARY DESC;
```

- 부서별로 연봉이 높은 사람 순서대로 정렬해서 알고싶다
```sql
SELECT *
FROM EMPLOYEE
ORDER BY dept_id asc, salary desc;
```

## aggregate function

- 여러 tuple들의 정보를 요약해서 하나의 값으로 추출하는 함수 
- 대표적으로 COUNT, SUM, MAX, MIN, AVG 함수가 있다
- (주로) 관심있는 attriubte에 사용된다.
  - AVG(salary), MAX(birth_date)
- NULL 값들은 제외하고 요약 값을 추출한다.

### aggregate function 예제1

임직원 수를 알고 싶다
```sql
SELECT count(*)
FROM EMPLOYEE;
```
- 튜플의 숫자를 의미한다.

```sql
SELECT count(position)
FROM EMPLOYEE;
```
- 중복을 포함해서 count 한다. 
- null 값은 제외한다. ➡️ 데이터에 null 값이 있으면 count 오류가 난다.
  - count(*)
  - count(id) - primary key
  - count(not null attribute)

### aggregate function 예제2

프로젝트 2002에 참여한 임직원 수와 최대 연봉과 최소 연봉과 평균 연봉을 알고 싶다 
```sql
SELECT count(*) as count, MAX(salary) as MAX_SALARY, MIN(salary) as MIN_SALARY, AVG(salary) as AVG_SALARY
FROM employee, works_on w
where id = w.empl_id and w.proj_id = 2002;
```

```sql
SELECT count(*) as count, MAX(salary) as MAX_SALARY, MIN(salary) as MIN_SALARY, AVG(salary) as AVG_SALARY
FROM works_on w join employee e on w.empl_id = e.id
where w.proj_id = 2002;
```
- join 사용해서 동일 결과 보이기 


## GROUP BY 

- 관심있는 attribute(s) 기준으로 그룹을 나눠서 aggregate function을 적용하고 싶을 때 사용 
- grouping attribute(s): 그룹을 나누는 기준이 되는 attribute(s)
- grouping attribute(s)에 NULL 값이 있을 때는 NULL 값을 가지는 tuple끼리 묶인다. 

### GROUP BY 예제1
- 각 프로젝트에 참여한 임직원의 수와 최대 연봉과 최소 연봉과 평균 연봉을 구하고 싶다 
```sql
SELECT w.proj_id, count(*) as count, MAX(salary) as MAX_SALARY, MIN(salary) as MIN_SALARY, AVG(salary) as AVG_SALARY
FROM works_on w join employee e on w.empl_id = e.id
group by w.proj_id;
```

### having

- 프로젝트 참여 인원이 4명 이상인 프로젝트들에 대해서 각 프로젝트에 참여한 임직원의 수와 
  최대 연봉과 최소 연봉과 평균 연봉을 구하고 싶다 

```sql
SELECT w.proj_id, count(*) as count, MAX(salary) as MAX_SALARY, MIN(salary) as MIN_SALARY, AVG(salary) as AVG_SALARY
FROM works_on w join employee e on w.empl_id = e.id
group by w.proj_id having count(*) >= 4;
```

- GROUP BY와 함께 사용된다
- aggregate function의 결과값을 바탕으로 그룹을 필터링하고 싶을 때 사용한다 
- HAVING절에 명시된 조건을 만족하는 그룹만 결과에 포함된다 

### 배운 개념 총합 예제1

- 각 부서별 인원수를 인원 수가 가장 많은 순서대로 정렬해서 알고 싶다 
```sql
SELECT dept_id, COUNT(*) as empl_count
FROM EMPLOYEE E 
group by E.dept_id
order by empl_count DESC;
```

- 각 부서별 - 성별 인원수를 인원수가 많은 순서대로 정렬해서 알고 싶다
```sql
SELECT dept_id, sex, COUNT(*) as empl_count
FROM EMPLOYEE E 
group by E.dept_id, sex
order by empl_count DESC;
```

### 배운 개념 총합 예제2

- 회사 전체 평균 연봉보다 평균 연봉이 적은 부서들의 평균 연봉을 알고 싶다

```sql
SELECT E.dept_id, AVG(E.salary)
FROM EMPLOYEE E           
group by E.dept_id
having AVG(E.salary) < (select AVG(salary)
						from employee);
```

### 배운 개념 총합 예제3

- 각 프로젝트 별로 프로젝트에 참여한 90년대생들의 수와 이들의 평균 연봉을 알고 싶다 
```sql
SELECT W.proj_id, COUNT(*) as 90year, ROUND(AVG(SALARY), 0)
FROM EMPLOYEE E join works_on W on E.id = W.empl_id 
where E.birth_date between '1990-01-01' AND '2000-01-01'
group by W.proj_id
order by W.proj_id;
```

- 프로젝트 참여 인원이 7명 이상인 프로젝트에 한정해서 
  각 프로젝트 별로 프로젝트에 참여한 90년대생들의 수와 이들의 평균 연봉을 알고 싶다 
```sql
SELECT W.proj_id, COUNT(*) as 90year, ROUND(AVG(SALARY), 0)
FROM EMPLOYEE E join works_on W on E.id = W.empl_id 
where E.birth_date between '1990-01-01' AND '2000-01-01'
		AND W.proj_id IN (SELECT proj_id 
                          FROM works_on
                          group by proj_id 
                          having count(*) >= 3)
group by W.proj_id 
order by W.proj_id;
```

### SELECT 요약 & 실행 순서 

```sql
SELECT attribute(s) or aggregate function(s)
FROM table(s)                               
[WHERE condition(s)]
[GROUP BY group attribute(s)]
[HAVING group condition(s)]
[ORDER BY attribute(s)]
```

```sql
6. SELECT attribute(s) or aggregate function(s)
1. FROM table(s)                                             
2. [WHERE condition(s)] 
3. [GROUP BY group attribute(s)]
4. [HAVING group condition(s)]
5. [ORDER BY attribute(s)]
```

- select 쿼리에서 각 절의 실행 순서는 개념적인 순서이다.
- select 쿼리의 실제 실행 순서는 각 RDBMS에서 어떻게 구현했는지에 따라 다르다




