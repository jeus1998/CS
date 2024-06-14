
# 고객 테이블 만들기

### 테이블 구조 

- 테이블명: Customer 
- 컬럼
  - 고객 고유 식별자 (키): CustomerId, int, Primary Key
    - Primary key: Not Null, Unique
  - 고객 이름: CustomerName, varchar(50), not null
    - varchar(50): ArrayList 느낌 
    - char(50): Array 느낌 
  - 고객 주소: CustomerAddress, varchar(200)
  - 고객 성별: CustomerSex, bit [0,1]
  - 고객 나이: CustomerAge, ``tinyint[1byte]`` = 255

### 테이블 생성 

```sql
CREATE DATABASE SQLTEST;
SHOW DATABASES;
USE SQLTEST;
SHOW TABLES;

CREATE TABLE CUSTOMER(
		CustomerId int PRIMARY KEY,
        CustomerName varchar(50) not null,
        CustomerAddress varchar(200),
        CustomerAge tinyint
);

desc customer;
```

