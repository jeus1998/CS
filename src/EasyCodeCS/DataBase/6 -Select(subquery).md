
# SQL 데이터 조회하기 - subquery

### 참고 자료
쉬운 코드 👉  https://www.youtube.com/watch?v=lwmwlA2WhFc&list=PLcXyemr8ZeoREWGhhZi5FZs6cvymjIBVe&index=6


### 💚 지난 내용 리마인드
IT 회사 RDB 만들기
- 부서, 사원, 프로젝트 관련 정보들을 저장할 수 있는 RDB 만들기 
- RDBMS -MySQL(InnoDB) 사용
![IT 회사 테이블.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2FIT%20%ED%9A%8C%EC%82%AC%20%ED%85%8C%EC%9D%B4%EB%B8%94.JPG)

  
## SELECT with subquery

### ❓ ID가 14인 임직원 보다 생년월일이 빠른 임직원의 ID, 이름, 생일 찾기

1. ID가 14인 임직원의 생일 구하기 
```sql
select birth_date 
from employee
where id = 14;
```
결과: 1992-08-04

2. 1992-08-14 보다 생년월일이 빠른 임직원의 ID, 이름, 생일 구하기
```sql
select id, name, birth_date
from employee
where birth_date < '1992-08-04';
```

```text
1	MEESI	1987-02-01
5	DINGYO	1990-11-05
6	JULIA	1986-12-11
9	HENRY	1982-05-20
10	NICOLE	1991-03-26
13	JISUNG	1989-07-07		
```

1개의 쿼리로 1번, 2번 실행하기 
```sql
select id, name, birth_date
from employee
where birth_date < (select birth_date 
					from employee 
                    where id = 14);
```

### ⭐️ subquery & outerquery 개념  

- subquery(nested query or inner query): select, insert, update, delete에 포함된 query
- outer query(main query): subquery를 포함하는 query
- subquery는 ()안에 기술 

### ❓ ID가 1인 임직원과 같은 부서 같은 성별인 임직원들의 ID와 이름과 직군을 알고 싶다

```sql
select id, name, position
from employee
where (dept_id, sex) = (select dept_id, sex
		                from employee
                        where id = 1);
                    
```

### ❓ ID가 5인 임직원과 같은 프로젝트에 참여한 임직원들의 ID를 알고 싶다

1. ID가 5인 임직원이 참여하고 있는 프로젝트 ID를 구한다. 
```sql
select proj_id
from works_on
where empl_id = 5;
```
-> 2001, 2002

2. 해당 프로젝트_ID로 ID가 5인 임직원을 제외하고 다른 임직원의 ID를 구한다.
```sql
select distinct empl_id
from works_on
where empl_id != 5 and (proj_id = 2001 or proj_id = 2002);
```

2번 쿼리 or -> in 사용하기 
```sql
select distinct empl_id
from works_on
where empl_id != 5 and proj_id in(2001, 2002);
```

1개의 쿼리로 1,2번 쿼리 진행하기
```sql
select distinct empl_id 
from works_on
where empl_id != 5 and proj_id in (select proj_id
                                  from works_on
                                  where empl_id = 5);
```

### ⭐️ IN & NOT IN 동작방식 
- v in (v1,v2,v3,...): v가 (v1,v2,v3,...)중에 하나와 값이 같다면 true return
- (v1,v2,v3,...)는 명시적인 값들의 집합 or subquery의 결과(set or multiset)
- v not in (v1,v2,v3,...): v가 (v1,v2,v3,...)의 모든 값과 다르다면 true return

### ⭐️⭐️ unqualified attribute가 참조하는 테이블 

- unqualified attribute: 테이블명을 명시하지 않은 attribute
```sql
select empl_id 
from works_on;
where proj_id = 1;
```
- 여기서 empl_id는 attribute(속성)인데 어디 테이블의 속성인지 명시하지 않았다. -> unqualified attribute
```sql
select distinct empl_id 
from works_on
where empl_id != 5 and proj_id in (select proj_id
                                  from works_on
                                  where empl_id = 5);
```
- unqulified attribute가 참조하는 테이블은 해당 attribute가 사용된 query를 포함하여 그 query의 바깥쪽으로 존재하는
  모든 queries중에 해당 attribute 이름을 가지는 가장 가까이에 있는 table을 참조한다.

- 첫 번째 줄 empl_id 가장 가까운 두 번째 줄의 works_on 참조 
- 세 번째 줄 where empl_id & proj_id는 두 번째 줄의 works_on 참조
- subquery 내에서 proj_id & empl_id는 subquery 안에서 wroks_on 참조 

### ❓ ID가 5인 임직원과 같은 프로젝트에 참여한 임직원들의 ID와 이름을 알고 싶다.

- 우리는 이미 같은 프로젝트에 참여한 임직원들의 ID를 구했다 해당 데이터를 통해서 ID, 이름을 구하자
- 임직원의 이름은 employee 테이블에 존재한다. 

```sql
select id, name
from employee
where id in (select distinct empl_id 
			 from works_on
             where empl_id != 5 and proj_id in (select proj_id
                                                from works_on
                                                where empl_id =5));                                  
                                  
```

### ⭐️from절 subquery에서 동일한 결과 보여주기

```sql
select id, name
from employee, 
     (select distinct empl_id
      from works_on
      where empl_id != 5 and proj_id in (select proj_id
                                         from works_on
										 where empl_id = 5)
     ) 
     as distinct_e # subquery를 통해 만든 가상의 테이블 
where id = distinct_e.empl_id;  #distinct_e 와 employee join condition                                 
```

### ❓ ID가 7 혹은 12인 임직원이 참여한 프로젝트의 ID와 이름을 알고 싶다

```sql
select id, name
 from project,
	  (select distinct proj_id
       from works_on
       where empl_id in (7, 12)
	  ) as distinct_p
 where id = distinct_p.proj_id;  
```

- EXISTS 활용해서 동일한 결과 보여주기 

```sql
 select p.id, p.name
 from project p
 where exists (
     select null
     from works_on w
     where p.id = w.proj_id and w.empl_id in (7,12)
 );
```

- EXISTS를 IN으로 바꿔서 동일한 결과 보여주기 

```sql
 select p.id, p.name
 from project p
 where p.id in (select w.proj_id
                from works_on w
                where p.id = w.proj_id and w.empl_id in (7, 12)
 );
```

- EXISTS와 IN은 서로 바꿔가면서 사용할 수 있다.

### ⭐️ EXISTS & NOT EXISTS
- correlated query: subquery가 바깥쪽 qeury의 attribute를 참조할 때, correlated subquery라 부름
- EXISTS : subquery의 결과가 최소 하나의 row라도 있으면 true return
- NOT EXISTS : subquery의 결과가 단 하나의 row라도 없다면 true return 

### ❓ 2000년대생이 없는 부서의 ID와 이름을 알고 싶다

```sql
 select d.id, d.name
 from department d
 where NOT EXISTS ( select null
			        from employee e
                    where e.dept_id = d.id and e.birth_date >= '2000-01-01' 
                  );   
```

- NOT EXISTS를 NOT IN으로 바꿔서 동일한 결과 보여주기 

```sql
select d.id, d.name
from department d
where d.id not in ( select e.dept_id
                    from employee e
                    where e.birth_date >= '2000-01-01'
);
```

### ❓ 리더보다 높은 연봉을 받는 부서원을 가진 리더의 ID와 이름과 연봉을 알고 싶다

```sql
select e.id, e.name, e.salary
from employee e
where e.id in (select d.leader_id
              from department d) and exists (select null
                                             from employee e2
                                             where e2.dept_id = e.dept_id and e.salary < e2.salary
								            );
```

- ANY를 사용해서 똑같은 결과 보여주기 

```sql
 select e.id, e.name, e.salary
 from employee e, department d
 where d.leader_id = e.id and e.salary < any (
			                                  select salary									
                                              from employee e2
                                              where e2.id != d.leader_id and  e.dept_id = e2.dept_id
                                             ); 
```      

### ⭐ ANY & SOME
- v comparison_operator ANY (subquery): subquery가 반환한 결과들 중에 단 하나라도 v와의 비교 연산이 true라면 true return
- SOME도 ANY와 같은 역할을 한다.

### ❓ 리더보다 높은 연봉을 받는 부서원을 가진 리더의 ID와 이름과 연봉을 알고 싶다 + 해당 부서에 속한 최고 연봉를 알고 싶다

- 추가로 해당 최고 연봉을 받는 사람의 id attribute

```sql
select e.id, e.name, e.salary, (                           
                               select max(salary)           
                               from employee
                               where dept_id = e.dept_id
                               ) as dept_max_salary , (
                                                       select id
                                                       from employee
                                                       where dept_id = e.dept_id and salary = dept_max_salary
                                                       ) as dept_max_salary_id
from employee e, department d
where d.leader_id = e.id and e.salary < any (
                                              select salary
											  from employee e2
                                              where e.dept_id = e2.dept_id
											);
```

- 이렇게 subquery는 select절에도 들어갈 수 있다.

### ❓ ID가 13인 임직원과 한번도 같은 프로젝트에 참여하지 못한 임직원들의 ID, 이름, 직군을 알고 싶다

```sql
select distinct e.id, e.name, e.position
from employee e, works_on w
where e.id = w.empl_id and w.proj_id != ALL (
											 select proj_id
                                             from works_on
											 where empl_id = 13
                                             );
 
```
### ⭐ ALL
- v comparison_operator All (subquery) : subquery가 반환한 결과들과 v와의 비교연산이 모두 true 라면 true 반환 

