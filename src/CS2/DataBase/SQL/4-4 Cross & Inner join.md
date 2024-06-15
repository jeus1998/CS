# Cross/Inner join

### CROSS JOIN

카르티시안 프로덕트 
- A X B
- CROSS JOIN

필요한 데이터 넣기 
```sql
CREATE TABLE A (
    id INT,
    value VARCHAR(50)
);

INSERT INTO A (id, value) VALUES
(1, 'A1'),
(2, 'A2');

CREATE TABLE B (
    id INT,
    description VARCHAR(50)
);

INSERT INTO B (id, description) VALUES
(1, 'B1'),
(2, 'B2'),
(3, 'B3');
```

두 결과 모두 동일하다.
```sql
SELECT * 
FROM A CROSS JOIN B
ORDER BY A.ID;

SELECT * 
FROM A,B
ORDER BY A.ID;
```

### INNER JOIN

PK, FK가 일치하는 경우만 보여주기 
```sql
SELECT * 
FROM A.B 
WHERE A.AID = B.AID;
```
- A.AID: A의 PK
- B.AID: A의 PK를 참조하고 있는 B의 FK


CUSTOMER 자동차구매고객 INNER 조인 
- 자동차구매고객의 CUSTOMERID와 CUSTOMER의 CUSTOMERID가 일치한 데이터만 보여주기  
```sql
SELECT * 
FROM CUSTOMER A JOIN 자동차구매고객 B ON A.CUSTOMERID = B.CUSTOMERID;
```

CUSTOMER & 자동차구매고객 & CAR INNER JOIN1
```sql
SELECT *
FROM CUSTOMER A, 자동차구매고객 B, CAR C
WHERE A.CUSTOMERID = B.CUSTOMERID AND B.CARID = C.CARID;
```

CUSTOMER & 자동차구매고객 & CAR INNER JOIN2
```sql
SELECT *
FROM CUSTOMER A
INNER JOIN 자동차구매고객 B ON A.CUSTOMERID = B.CUSTOMERID
INNER JOIN CAR C ON B.CARID = C.CARID;
```