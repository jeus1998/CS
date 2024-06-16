# SELECT 문에서 서브 쿼리

### 고객중에 여성고객과 남성고객 수를 구하시오 

GROUP BY 활용 
```sql
SELECT count(*) AS 고객수 
FROM customer
GROUP BY CUSTOMERSEX;
```
```text
고객수 
6
5
```

CASE + GROUP BY 활용
```sql
SELECT CASE WHEN CUSTOMERSEX = 0 THEN '여성' ELSE '남성' END AS 성별, COUNT(*) AS 고객수 
FROM customer
GROUP BY CUSTOMERSEX;
```

```text
성별    고객수
남성	  6
여성	  5
```

NULL 값 고려해서 CASE + GROUP BY 활용 
```sql
SELECT
CASE CUSTOMERSEX
WHEN CUSTOMERSEX = 0 THEN '여성'
WHEN CUSTOMERSEX = 1 THEN '남성'
ELSE '미확인'
END AS 고객구분 , COUNT(*) 고객수
FROM CUSTOMER
GROUP BY CUSTOMERSEX;
```

```markdown
| 고객구분 | 고객수 |
|---------|-------|
| 남성     | 6     |
| 남성     | 5     |
| 미확인   | 1     |
```

UNION 사용하기
```SQL
SELECT '남성고객' 고객구분, count(*) 고객수 from customer where customersex = 0
union
SELECT '여성고객' 고객구분, count(*) 고객수 from customer where customersex = 1
union
SELECT '미확인고객' 고객구분, count(*) 고객수 from customer where customersex is null;
```


SELECT문 서브 쿼리, 스칼라 쿼리 
```sql
select (select count(*) from customer where customersex = 1) 여성고객,
	   (select count(*) from customer where customersex = 0) 남성고객,
       (select count(*) from customer where customersex is null ) 미확인고객;
```

### 고객중에 여성고객과 남성고객 수를 구하시오 + 자동차 총 판매대수와 총 매출금액을 함께 구하시오 

SELECT문 서브 쿼리, 스칼라 쿼리 
```sql
select (select count(*) from customer where customersex = 1) 여성고객,
	   (select count(*) from customer where customersex = 0) 남성고객,
       (select count(*) from customer where customersex is null ) 미확인고객,
       (select sum(구매가격) from 자동차구매고객) 총매출금액,
       (select count(*) from 자동차구매고객) 자동차총판매대수;
```

남자, 여자, NULL에 해당하는 고객수, 매출금액, 자동차판매대수가 나온다 
```sql
SELECT 
	CASE 
		WHEN CUSTOMERSEX = 0 THEN '남성'
		WHEN CUSTOMERSEX = 1 THEN '여성'
		ELSE '미확인'
	END AS 고객구분, COUNT(*) 고객수, SUM(B.구매가격) AS 총매출금액, count(구매가격) AS 자동차총판매대수 
FROM CUSTOMER A LEFT OUTER JOIN 자동차구매고객 B ON A.CustomerId = B.CustomerId
GROUP BY CUSTOMERSEX;

```