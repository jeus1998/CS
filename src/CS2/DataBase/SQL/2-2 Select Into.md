# Select Into

### 공백 없애서 select 결과 보기 

- 고객 주소 공백 없이 select
```sql
select replace(CustomerAddress, ' ', '') as no_space_address from customer;
```

```sql
select * 
from (select CustomerId, CustomerName, replace(CustomerAddress, ' ', '') addr , CustomerSex, CustomerAge from customer) A
where A.addr = '대한민국서울특별시마포구';
```

### SELECT INTO 예제 

Microsoft SQL Server 문법
```sql
select * 
into SeoulMapoCustomer
from customer c
where c.CustomerAddress = '대한민국 서울 특별시 마포구';
```

MySQL에는 SELECT INTO 문법이 없다.
```sql
create TABLE SeoulMapoCustomer
SELECT * 
FROM CUSTOMER
WHERE CUSTOMERAddress =  '대한민국 서울 특별시 마포구';
```
