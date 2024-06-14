
# Insert1

### Customer 테이블 성별 컬럼 넣어서 다시 만들기

```sql
DROP TABLE customer;

CREATE TABLE CUSTOMER(
		CustomerId int PRIMARY KEY,
        CustomerName varchar(50) not null,
        CustomerAddress varchar(200),
        CustomerSex bit,
        CustomerAge tinyint
);
```

### INSERT 문법

```sql
INSERT INTO [테이블이름]
(col1, col2, ...) 
VALUES(v1, v2, ...)
```

모든 컬럼 삽입 case
```sql
INSERT INTO [테이블이름]
VALUES(v1, v2, ...)
```

### 예제

```sql
insert into customer (CustomerId, CustomerName, CustomerAddress, CustomerSex, CustomerAge)
values(1, '홍길동', '대한민국 서울 특별시 마포구', 1, 20);

insert into customer (CustomerId, CustomerName, CustomerAddress, CustomerSex, CustomerAge)
values(2, '김길동', '대한민국 서울 특별시 마포구', 0, 21);

insert into customer (CustomerId, CustomerName, CustomerAddress, CustomerSex, CustomerAge)
values(3, '박길동', '대한민국 서울 특별시 마포구', 1, 22);

insert into customer (CustomerId, CustomerName, CustomerAddress, CustomerSex, CustomerAge)
values(4, '이길동', '대한민국 서울 특별시 마포구', 0, 23);

insert into customer (CustomerId, CustomerName, CustomerAddress, CustomerSex, CustomerAge)
values(5, '최길동', '대한민국 서울 특별시 마포구', 1, 24);

# CustomerAge 생략 버전 
insert into customer (CustomerId, CustomerName, CustomerAddress, CustomerSex)
values(6, '황진이', '대한민국 서울 특별시 마포구', 0);

# 전체 컬럼에 대해서 데이터를 넣는 경우 컬럼명 생략     
insert into customer values(7, '최길동', '대한민국 서울 특별시 마포구', 1, 24);
```

