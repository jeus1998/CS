
# SELECT 데이터 조회하기(JOIN)

### SQL에서 JOIN이란?

- 두 개 이상의 table들에 있는 데이터를 한 번에 조회하는 것 
- 여러 종류의 JOIN이 존재한다

## implicit join vs explicit join

### implicit join

![40.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F40.JPG)

- from절에는 table들만 나열하고 where절에 join condition을 명시하는 방식 
- old-style join syntax
- where 절에 selection condition과 join condition이 같이 있기 때문에 가독성이 떨어진다
- 복잡한 join 쿼리를 작성하다 보면 실수로 잘못된 쿼리를 작성할 가능성이 크다

### explicit join

```sql
SELECT D.name
FROM employee AS E JOIN department AS D ON E.dept_id = D.id
WHERE E.id = 1;
```
- explicit join: from절에 JOIN 키워드와 함께 joined table들을 명시하는 방식 
- from 절에서 ON 뒤에 join condition이 명시된다
- 가독성이 좋다 
- 복잡한 join 쿼리 작성 중에도 실수할 가능성이 적다

## inner join vs outer join

### inner join

```sql
SELECT *
FROM employee E INNER JOIN department D ON E.dept_id = D.id;
```

![41.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F41.JPG)

INNER JOIN 결과 
![42.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F42.JPG)

- 두 테이블에서 join condition을 만족하는 tuple들로 result table을 만드는 join
- ``FROM table1 [INNER] JOIN table2 ON join_condition``
- join condition에 사용 가능한 연산자(operator):``=, <, >, !=`` 등등 여러 비교 연산자가 가능하다
- join condition에서 null 값을 가지는 tuple은 result table에 포함되지 못한다 
  - join condition이 true
  - unknown 포함 ❌

### outer join

- 두 테이블에서 join condition을 만족하지 않는 tuple들도 result table에 포함하는 join
- FROM table1 LEFT [OUTER] JOIN table2 ON join_condition
- FROM table1 RIGHT [OUTER] JOIN table2 ON join_condition
- FROM table1 FULL [OUTER] JOIN table2 ON join_condition
- join condition에 사용 가능한 연산자(operator):``=, <, >, !=`` 등등 여러 비교 연산자가 가능하다

### left outer join

![43.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F43.JPG)

LEFT OUTER JOIN 결과
![44.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F44.JPG)

### right outer join

![45.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F45.JPG)

RIGHT OUTER JOIN 결과
![46.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F46.JPG)

### full outer join

![47.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F47.JPG)
- MySQL: full outer join 지원 ❌
- PostgreSQL: full outer join 지원 ⭕️

FULL OUTER JOIN 결과
![48.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F48.JPG)


### equi join

- join condition에서 = (equality comparator)를 사용하는 join
- equi join에 대한 두 가지 시각
  - inner join outer join 상관없이 = 를 사용한 join이라면 equi join으로 보는 경우 
  - inner join으로 한정해서 = 를 사용한 경우에 equi join으로 보는 경우

### using

USING 사용하기 전 
```sql
SELECT *
FROM employee E INNER JOIN department D ON E.dept_id = D.id;
```
![41.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F41.JPG)

USING 사용후 
```sql
SELECT * 
FROM employee E INNER JOIN department D USING (dept_id);
```
![49.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F49.JPG)


- 두 table이 equi join할 때 join하는 attribute의 이름이 같드면, USING으로 간단하게 작성할 수 있다. 
- 이 때 같은 이름의 attribute는 result table에서 한번만 표시 된다 
- FROM table1 [INNER] JOIN table2 USING (attribute(s))
- FROM table1 LEFT [OUTER] JOIN table2 USING (attribute(s))
- FROM table1 RIGHT [OUTER] JOIN table2 USING (attribute(s))
- FROM table1 FULL [OUTER] JOIN table2 USING (attribute(s))

### natural join

- 두 테이블에서 같은 이름을 가지는 모든 attribute pair에 대해서 equi join을 수행 
- join condition을 따로 명시하지 않는다.
- FROM table1 NATURAL [INNER] JOIN table2 USING (attribute(s))
- FROM table1 NATURAL LEFT [OUTER] JOIN table2 USING (attribute(s))
- FROM table1 NATURAL RIGHT [OUTER] JOIN table2 USING (attribute(s))
- FROM table1 NATURAL FULL [OUTER] JOIN table2 USING (attribute(s))

NATURAL INNER JOIN 
![50.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F50.JPG)

NATURAL INNER JOIN 결과
![51.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F51.JPG)

### cross join

- 두 table의 tuple pair로 만들 수 있는 모든 조합(= Cartesian product)을 result table로 반환한다
- join conidition이 없다
- implicit cross join: FROM table1, table2
- explicit cross join: FROM table1 CROSS JOIN table2

```sql
SELECT *
FROM employee CROSS JOIN department;
```

![52.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F52.JPG)

cross join @MySQL
- MySQL에서는 cross join = inner join = join 
- CROSS JOIN에 ON(or USING)을 같이 쓰면 inner join으로 동작한다
- INNER JOIN(or JOIN)에 ON(or USING)없이 사용되면 cross join으로 동작한다.

### self join
- table이 자기 자신에게 join하는 경우

### join example

- ID가 1003인 부서에 속하는 임직원 중 리더를 제외한 부서원의 ID, 이름, 연봉을 알고 싶다
```sql
SELECT E.ID, E.NAME, E.SALARY               
FROM employee E INNER JOIN department D ON E.dept_id = D.id
where E.id != D.leader_id AND D.id = 1003;      
```

- ID가 2001인 프로젝트에 참여한 임직원들의 이름과 직군과 소속 부서 이름을 알고 싶다
```sql
select E.name, E.position, D.name
from works_on W join employee E on W.empl_id = E.id
				left outer join department D on e.dept_id = D.id
where W.proj_id = 2001; 
```


