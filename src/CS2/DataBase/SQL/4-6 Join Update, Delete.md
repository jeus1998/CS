# Join Update, Delete


### MYSQL - JOIN UPDATE

```sql
UPDATE CUSTOMER A
JOIN 자동차구매고객 B
ON A.CUSTOMERID = B.CUSTOMERID
SET 구매가격=30000000
WHERE A.CUSTOMERID = 2;
```

### MYSQL - JOIN DELETE

```sql
DELETE A
FROM 자동차구매고객  A
JOIN CUSTOMER B ON A.CUSTOMERID = B.CUSTOMERID
WHERE A.CUSTOMERID = 2;
```
- DELETE 에서 JOIN을 사용하려고 하면 정확하게 대상 테이블을 지정해야 한다. 