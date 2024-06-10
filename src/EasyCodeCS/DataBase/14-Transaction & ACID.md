# Transaction & ACID

## Transaction

### 예제로 배우는 트랜잭션 개념

- J가 H에게 20만원을 이체한다면 각좌의 계좌는 어떻게 변경돼야 할까?

![68.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F68.JPG)
- 2개의 SQL statement 모두 성공을 해야지 이체 작업이 완료된다.
- 만약 J의 계좌에서 -20만원이 실패 했다면?
  - 결과가 이상해진다. J계좌는 그대로인데 H계좌만 + 20만원
- 즉 둘 다 정상 처리돼야만 성공하는 단일 작업 
  - ➡️ Transaction

⭐️ Transaction
- 단일한 논리적인 작업 단위(a single logical unit of work)
- 논리적인 이유로 여러 SQL문들을 단일 작업으로 묶어서 나눠질 수 없게 만든 것이 Transaction 이다.
- Transaction의 SQL문들 중에 일부만 성공해서 DB에 반영되는 일은 일어나지 않는다

### SQL을 통해 트랜잭션 사용해 보기

J가 H에게 20만원 이체 트랜잭션을 사용해서 구현 
- MySQL

```sql
START TRANSACTION;

UPDATE account SET balance = balance - 200000 WHERE id = 'J';

UPDATE account SET balance = balance + 200000 WHERE id = 'H';

COMMIT;
```
- ✅``START TRANSACTION``
  - 트랜잭션 시작 
- ✅``COMMIT``
  - 지금까지 작업한 내용을 DB에 영구적으로(permanently)저장 
  - transaction을 종료한다 
- J: 8000000
- H:22000000

추가로 J가 H에게 30만원 이체한 것을 Transaction으로 구현 
```sql
START TRANSACTION;

UPDATE account SET balance = balance - 300000 WHERE id = 'J';

ROLLBACK;
```
- ✅``ROLLBACK``
  - 지금까지 작업들을 모두 취소하고 transaction 이전 상태로 되돌린다
  - transaction 종료


ROLLBACK 확인
```sql
SELECT * FROM ACCOUNT;
```
- J:8000000
- H:22000000

### AUTOCOMMIT

✅AUTOCOMMIT
- 각각의 SQL문을 자동으로 transaction 처리 해주는 개념 
- SQL문이 성공적으로 실행하면 자동으로 commit 한다
- 실행 중에 문제가 있으면 알아서 rollback 한다
- MySQL에서는 dafault로 autocommit이 enabled 되어 있다 
- 다른 RDBMS에서도 대부분 같은 기능을 제공한다 

현재 autocommit 활성화 여부 확인 방법 
```sql
SELECT @@AUTOCOMMIT;
``` 
- 1: true
- 0: false
- MySQL은 true, false ❌ ➡️ tiny int 표현(1bit)

```sql
START TRANSACTION;
```
- 실행과 동시에 autocommit은 off가 된다 
- COMMIT/ROLLBACK과 함께 transaction이 종료되면 원래 autocommit 상태로 돌아간다
  - 1 ➡️ 1, 0 ➡️ 0

### 일반적인 트랜잭션 사용 패턴

1. transaction 시작(begin)한다
2. 데이터를 읽고 쓰는 등의 SQL문을 포함해서 로직을 수행 
3. 일련의 과정들이 문제없이 동작 했다면 transaction commit
4. 중간에 문제가 발생했다면 transaction rollback 한다 

### Java에서 트랜잭션 사용 예제

```java
import java.sql.Connection;

public class Bank {
  // ... 
    public void transfer(String fromId, String told, int amount) {
        try {
          Connection connection = new Connection();
          connection.setAutoCommit(false);         // START TRANSACTION
          // ...
          // ...
          connection.commit();
        } catch (Exception e) {
          //...
          connection.rollback();
          //...
        } finally {
          connection.setAutoCommit(true);
        }
    }
}
```

스프링 
```java
public class Bank {
    // ... 
    @Transactional
    public void transfer(String fromId, String told, int amount) {
        // ... 로직
    }
}
```

## 트랜잭션 속성 - ACID 

- Atomicity
- Consistency
- Isolation
- Durability

### Atomicity

![69.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F69.JPG)
- 모두 성공하거나
- 모두 실패하거나
- 원자성 
- All or NOTHING
- transaction은 논리적으로 쪼개질 수 없는 작업 단위이기 때문에 내부의 SQL statement 모두 성공해야 한다 
- 중간에 SQL문이 실패하면 지금까지의 작업을 모두 취소하여 아무 일도 없었던 것처럼 rollback 한다 

역할
- commit 실행 시 DB에 영구적으로 저장하는 것은 DBMS가 담당하는 부분
- rollback 실행 시 이전 상태로 되돌리는 것도 DBMS가 담당하는 부분 
- 개발자는 언제 commit 하거나 rollback 할지를 챙겨야 한다 

### Consistency

![70.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F70.JPG)
- 일관성 
- transaction은 DB 상태를 consistent 상태에서 또 다른 consistent 상태로 바꿔줘야 한다 
- constraints, trigger 등을 통해 DB에 정의된 rules을 transaction이 위반했다면 rollback 해야 한다
- transaction이 DB에 정의된 rule을 위반했는지는 DBMS가 commit 전에 확인하고 알려준다
- 그 외에 application 관점에서 transaction이 consistent하게 동작하는지는 개발자가 챙겨야 한다 

### Isolation

![71.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F71.JPG)
- 격리성 
- 여러 transaction들이 동시에 실행될 때도 혼자 실행되는 것처럼 동작하게 만든다
- DBMS는 여러 종류의 isolaton level을 제공한다 
- 개발자는 isolation level 중에 어떤 level로 transaction을 동작시킬지 설정할 수 있다 
- concurrency control의 주된 목표가 isolation이다 

### Durability

![72.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F72.JPG)
- 영존성 
- commit된 transaction은 DB에 영구적으로 저장한다 
- 즉 DB system에 문제(power fail or DB crash)가 생겨도 commit된 transaction은 DB에 남아 있는다 
- '영구적으로 저장한다' 라고 할 때는 일반적으로 '비휘발성 메모리(HDD,SDD..)'에 저장함'을 의미한다
- 기본적으로 transaction durability는 DBMS가 보장한다




