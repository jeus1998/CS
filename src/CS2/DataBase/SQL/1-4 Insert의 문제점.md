# Insert의 문제점

### 골치아픈 문제

```text
세 명이 c1,c2,c3 고객을 각각 입려하려고 한다.
이들은 입력 전 CustomerId(PK)를 확인해보니 2번까지 들어가 있음을 각자 확인 
이들은 모두 고객 등록을 성공할 수 있을까? 아니면 결과는 무엇일까?
```

c1,c2,c3 고객 중에 제일 먼저 insert(3, ....)을 실행한 사람만 고객 등록이 된다.

### 해결책: 자동 증분

- auto increment 
- DataBase 알아서 pk를 넣어준다. (요청 순서에 따라서 정확히는 트랜잭션 처리 순서에 따라)

설정 방법1
  - 변경 테이블 우클릭 해당 컬럼 AI 체크
  - ![7.JPG](Image%2F7.JPG)
  - apply

설정 방법2 (query)
```sql
ALTER TABLE customer
CHANGE COLUMN `CustomerId` `CustomerId` INT NOT NULL AUTO_INCREMENT ;
```

확인 하기 
```sql
insert into customer (CustomerName, CustomerAddress, CustomerSex)
values('제웅', '대한민국 서울 특별시 마포구', 0);
```
- 자동으로 CustomerId가 1 증가해서 튜플(레코드)이 생겼다.

자동 증가 필드에 값 넣기 
```sql
INSERT INTO customer (CustomerID, CustomerName, CustomerAddress, CustomerSex)
VALUES (20, '제웅2', '대한민국 서울 특별시', 1);
```
- CustomerId 20으로 해당 데이터가 들어간다.
- 이 이후로 CustomerId 필드를 생략하고 값을 넣으면 해당 테이블의 최대 ID에서 1씩 증가되는 형태로 다시 데이터가 들어간다.


