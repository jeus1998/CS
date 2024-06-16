# View 사용하기

### 뷰 정의

- 뷰는 가상테이블 또는 저장된 쿼리 
- 실제 테이블이 아니라 쿼리문을 저장하고 있으며 테이블과 같이 쿼리문을 통해서 조회 가능 

### 뷰 사용 이유

- 원하는 컬럼들, 레코드(타게팅)를 사전에 만들어 쿼리로 저장함으로써 불필요한 반환값을 최대한 제거 가능 
- 복잡한 Join문 등을 뷰로 저장함으로써 단일 테이블을 사용하듯 간단한 쿼리로 사용 
- 실제적 테이블 내부 구조를 숨길 수 았다 
- VIEW는 READ ONLY - SELECT 목적으로 사용하자 

### 뷰 예시

뷰 생성 
```sql
CREATE VIEW CUSTOMER_ORDER_CAR AS
SELECT A.CUSTOMERID 고객ID, A.CUSTOMERNAME 고객이름, A.CUSTOMERSEX 고객성별, 
       A.CUSTOMERADDRESS 고객주소, B.구매일자 , B.구매가격, C.CARNAME, C.MAKER
FROM CUSTOMER A 
JOIN 자동차구매고객 B ON A.CUSTOMERID = B.CUSTOMERID
JOIN CAR C ON B.CARID = C.CARID;

SELECT * FROM customer_order_car;
```
- 뷰 안에서는 ORDER_BY 불가 

뷰 사용
```sql
SELECT * FROM customer_order_car;
```

뷰 삭제
```sql
DROP VIEW customer_order_car;
```