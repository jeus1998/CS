# transaction isolation level & snapshot isolation

## 이상현상 

### Dirty Read

![20.JPG](Image%2F20.JPG)
- commit 되지 않은 변화를 읽음 

### Non-repeatable Read

![21.JPG](Image%2F21.JPG)

### Phantom read

![22.JPG](Image%2F22.JPG)
- 없던 데이터가 생김

## isolation level

- 모든 이상현상을 막으면 가능은 하지만 제약사항이 많다
- 제약사항이 많아지면 DB 성능이 낮아진다
- 일부 이상한 현상은 허용하는 몇 가지 level을 만들자
- 사용자가 필요에 따라 적절하게 선택할 수 있도록 하자!
- ➡️ isolation level

![23.JPG](Image%2F23.JPG)
- Serializable level은 세 가지 이상 현상을 제외하고 어떤 이상 현상도 일어나지 않는다

isolation level 
- 세 가지 이상 현상을 정의하고 어떤 현상을 허용하는지에 따라서 각각의 isolation level이 구분된다
- 애플리케이션 설계자는 isolation level을 통해 전체 처리량과 데이터 일관성 사이에서 어느 정도 거래(trade)를
  할 수 있다
- 1992년 11월에 발표한 SQL 표준에서 정의된 내용 

### standard SQL 92 isolation level 비판 

1. 세 가지 이상 현상의 정의가 모호 
2. 이상 현상은 세 가지 외에도 더 있다
3. 상업적인 DBMS에서 사용하는 방법을 반영해서 isolation level을 구분하지 않았다 

### dirty write

- x = 0 시작 
- transaction 1: x를 10으로 바꾼다 
- transaction 2: x를 100으로 바꾼다 

![24.JPG](Image%2F24.JPG)
- rollback 시 정상적인 recovery는 매우 중요하기 때문에 모든 isolation level에서
  dirty write를 허용하면 안된다 

### lost update 

- x = 50 시작 
- transaction 1: x에 50을 더한다 
- transaction 2: x에 150을 더한다 

![25.JPG](Image%2F25.JPG)

### Dirty Read 확장판

![26.JPG](Image%2F26.JPG)
- abort가 발생하지 않아도 dirty read가 될 수 있다

### SNAPSHOT ISOLATION

![27.JPG](Image%2F27.JPG)
- 실제 구현에 기반해서 isolation level 정의
- 상업적인 DBMS에서 사용되는 방법을 반영해서 iolation level을 구분하지 않았다. ➡️ SNAPSHOT ISOLATION
- 기존 표준에서 정의한 iolation level은 이상현상을 바탕으로 어느정도 허용하는지에 따라 구분 
- SNAPSHOT ISOLATION은 concurrency control 구현에 기반하여 정의된 isolation  

예제
- init data: x = 50, y = 50 
- transaction1: x가 y에 40을 이체 
- transaction2: y에 100을 입금 

![28.JPG](Image%2F28.JPG)

### RDBMS isolation level 

MySQL(innoDB)
![29.JPG](Image%2F29.JPG)
- 표준 SQL과 동일 

Oracle
![30.JPG](Image%2F30.JPG)
- Read uncommitted level 제공 ❌
- Read committed
- REPETABLE READ ➡️ SERIALIZABLE
- SERIALIZABLE ➡️ SERIALIZABLE 
- SERIALIZABLE: SNAPSHOT ISOLATION 과 동일하게 동작

SQL server
![31.JPG](Image%2F31.JPG)
- 표준 SQL과 동일 
- + Snapshot

PostgreSQL
![32.JPG](Image%2F32.JPG)
- Repeatable read = SNAPSHOT ISOLATION LEVEL 
- 이상현상 추가

RDBMS 
- 주요 RDBMS는 SQL 표준에 기반해서 ISOLATION LEVEL을 정의 
- RDBMS마다 제공하는 ISOLATION LEVEL이 다르다
- 같은 이름의 ISOLATION LEVEL이라도 동작 방식이 다를 수 있다 
- 사용하는 RDBMS ISOLATION LEVEL을 잘 파악해서 적절한 LEVEL을 사용할 수 있도록 하자 


