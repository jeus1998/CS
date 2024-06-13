# 생활코딩 MySQL 기초 

### MySQL Monitor - (윈도우)

MySQL Client 정의
- MySQL Monitor: MySQL Server를 사용하는 MySQL Client (CLI)
- MySQL WorkBench 또한 MySQL Client (GUI)
- Web Application: MySQL Client(멀티 쓰레딩: n개의 Client) (Web)

MySQL Monitor 특징
- CLI 기반 
- MySQL 다운을 받으면 기본 내장

실행 방법(MySQL Monitor) 
- 디렉토리 이동
- MySQL 실행: ``mysql -u [사용자 이름] -p``
   - ``mysql -u [root] -p``
   - ``-h``를 생략하면 로컬 호스트(IP 주소, 127.0.0.1)를 의미함 
- 비밀번호 입력 
- Your MySQL connection id is ... 나오면 성공 (커넥션 연결, 세션 형성 완료 의미)
- MySQL 종료: ``exit``

### MySQL(데이터베이스) 기본 개념 

- 구조: MySQL 데이터베이스 서버 > 데이터베이스(스키마) > Tables(표) 
- SQL: Structured Query Language

### MySQL 기본 Query (까먹은 내용만 정리)

현재 데이터베이스(스키마) 보기 
```sql
show databases;
```

사용하고 싶은 데이터베이스(스키마) 선택
- ``use [데이터베이스];``
```sql
use practice;
```

현재 스키마에 있는 테이블 보기
```sql
show tables;
```

테이블 컬럼 속성 확인 
- ``DESC [테이블명]``
```sql
DESC TOPIC;
```

SELECT LIMIT 제한 걸기 
- 해당 튜플의 3개까지 출력 
```sql
SELECT * FROM topic LIMIT 3;
```

테이블 이름 변경하기 
- ``rename TABLE [기존 이름]to [새로운 이름]``
```sql
rename TABLE topic to topic_backup;
```







