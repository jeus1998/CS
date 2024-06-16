# WHERE절에서 서브 쿼리

### 자동차를 한대 이상 구매한 고객에 대한 상세정보를 구하시오 

```sql
SELECT * 
FROM CUSTOMER
WHERE CUSTOMERID IN (SELECT CUSTOMERID 
					 FROM 자동차구매고객);
```

똑같은 정보 INNER JOIN을 통해서 찾기 
```sql
SELECT A.CustomerId, A.CustomerName, A.CustomerAddress, A.CustomerSex, A.CustomerAge
FROM CUSTOMER A JOIN 자동차구매고객 B ON A.CUSTOMERID = B.CUSTOMERID;
```

### 상관 서브 쿼리 

내부의 쿼리에서 외부 쿼리 테이블의 데이터를 참조하는 쿼리
- 메인 쿼리의 테이블의 행마다 서브쿼리가 반복 실행됨
- 다른 행의 열끼리 비교하는 쿼리

