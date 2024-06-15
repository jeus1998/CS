# OUTER JOIN


###  OUTER JOIN 

고객들의 정보와 함께 차를 샀는지 안 샀는지 정보도 같이 보고 싶다. 
- 그렇다면 고객의 정보는 전부 보여야 하지만 해당 고객은 차를 샀을 수도 있고 안 샀을 수도 있다.
- LEFT OUTER JOIN
```sql
SELECT * 
FROM CUSTOMER A LEFT OUTER JOIN 자동차구매고객 B ON A.CUSTOMERID = B.CUSTOMERID;
```


### FULL OUTER JOIN in MySQL

- MySQL에는 FULL OUTER JOIN이 없다. 
- 그래서 UNION을 사용해서 보여준다.
```sql
SELECT *
FROM CUSTOMER A
LEFT JOIN 자동차구매고객 B ON A.CUSTOMERID = B.CUSTOMERID
UNION ALL
SELECT *
FROM CUSTOMER A
RIGHT JOIN 자동차구매고객 B ON A.CUSTOMERID = B.CUSTOMERID
WHERE A.CUSTOMERID IS NULL;
```
- LEFT JOIN & RIGHT JOIN UNION ALL -> A,B 교집합이 2번 나온다.
- LEFT JOIN & RIGHT JOIN UNION -> A,B 교집합이 1번 나온다.

![18.JPG](Image%2F18.JPG)