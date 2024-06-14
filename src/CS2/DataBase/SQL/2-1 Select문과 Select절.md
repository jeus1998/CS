# Select문과 Select절

### SELECT statement

```sql
SELECT [ALL | DISTINCT] 컬럼명, 컬럼명
[INTO 테이블명]
FROM 테이블명, [테이블명, ...]
[WHERE 조건식]
[GROUP BY 컬럼명 [HAVING 조건식]]
[ORDER BY 컬럼명]
```

### ALL / DISTINCT

- ALL
  - 테이블에 동일한 데이터 행(튜플)이 있는 경우에도 모든 데이터를 반환한다. 
  - 지정하지 않으면 default ALL
- DISTINCT
  - 테이블에 동일한 데이터 행이 있는 경우 중복을 제거한 1개만을 반환 

### 컬럼 별칭 

- 컬럼명[AS] 컬럼 별칭 (AS는 생략 가능)
- 컬럼을 다른 이름으로 표시할 수 있다.
- WHERE, GROUP BY, HAVING은 컬럼 별칭이 없다.

### 예제 ALL/DISTINCT & AS

MySQL 허용 문법 
```sql
SELECT *, CUSTOMERID
FROM CUSTOMER;
```

MySQL 허용 X 문법
```sql
SELECT CUSTOMERID, *
FROM CUSTOMER;
```

성별 종류 출력하기
```sql
SELECT DISTINCT CUSTOMERSEX
from customer;
```

as 사용하기
```sql
select customerId as 고객관리번호, customerName as 고객명, customerAddress 주소
from customer;
```
