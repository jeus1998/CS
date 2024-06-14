# Insert2 & CSV 파일 DB에 넣기 

## MySQL CSV 파일 넣기 

### CSV 파일 생성하기 

- 엑셀 파일 생성 
- ![1.JPG](Image%2F1.JPG)


- CSV 파일로 저장 
- ![2.JPG](Image%2F2.JPG)

### CSV 파일 삽입하기

- 1: Table Data Import Wizard 선택
  - 해당 데이터베이스 우클릭
  - Table Data Import Wizard 선택 

- 2: 넣을 CSV 파일 선택
  - ![3.JPG](Image%2F3.JPG)

- 3: 테이블 생성 또는 선택
  - using existing table 
  - create new table
  - ![4.JPG](Image%2F4.JPG)
- 4: 데이터 포멧 설정/ 확인하기
  - 인코딩 타입이 utf-8인지 확인
  - 필드 타입이 일치하는지 확인
  - ![5.JPG](Image%2F5.JPG)
- 5: 데이터 확인
  - ``select * from data;``
  - ![6.JPG](Image%2F6.JPG)
- 6: 참고 자료 
  - [참고 자료](https://velog.io/@99mon/Mysql-CSV-%ED%8C%8C%EC%9D%BC-%EB%84%A3%EA%B8%B0)

### INSERT2

csv 파일을 MySQL 테이블에 추가하고 해당 데이터를 기존 테이블에 Insert 하기 

```sql
INSERT INTO table2(col, col2)
SELECT * from table1
WHERE  condition;
```

실제 쿼리 
```sql
insert into customer
select * from data;
```
