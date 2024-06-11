# concurrency control 기초 - schedule & serializability

### 목차
- schedule
- serializability
- isolation울 보장하는 기초 이론 

### Schedule - 실행 순서 case

- K가 H에게 20만원을 이체할 때 H도 ATM에서 본인 계좌에 30만원을 입금한다면 
  여러 형태의 실행이 가능할 수 있다 

CASE1
![1.JPG](Image%2F1.JPG)

CASE2
![2.JPG](Image%2F2.JPG)

CASE3
![3.JPG](Image%2F3.JPG)

CASE4
![4.JPG](Image%2F4.JPG)
- Lost Update!!
- 30만원 입금이 날라갔다 

operation 개념 
![5.JPG](Image%2F5.JPG)

간소화
- operation: read(k_balance -> 100): r1(k)
- operation: write(k_balance -> 80): w1(k)
- .... 생략

전체 CASE 실행순서 간략화
![6.JPG](Image%2F6.JPG)

### Schedule
- 여러 transaction들이 동시에 실행될 때 각 transaction에 속한 operaton들의 실행 순서 
- 각 transaction 내의 operation들의 실행 순서는 바뀌지 않는다

### Serial Schedule

![7.JPG](Image%2F7.JPG)
- transaction들이 겹치지 않고 한 번에 하나씩 실행되는 schedule

### NonSerial Schedule

![8.JPG](Image%2F8.JPG)
- transaction들이 겹쳐서 실행되는 schedule

### Serial Schedule 성능 

![9.JPG](Image%2F9.JPG)

- 한 번에 하나의 transaction만 실행되기 때문에 좋은 성능을 낼 수 없다.
  - 동시성이 없다 
- 하나의 transaction이 DB와 I/O작업을 하는 동안 CPU 대기 상태 
  - CPU 자원 낭비 
  - 많은 트랜잭션이 순차적으로 실행되면, 대기 시간이 누적되어 전체 성능이 저하
- 현실적으로 사용할 수 없는 방식 

### NonSerial Schedule 성능 

![10.JPG](Image%2F10.JPG)
- transaction들이 겹처서 실행되기 때문에 동시성이 높아져서 같은 시간 동안 더 많은 
  transaction들이 처리할 수 있다. 
- ex) transaction1이 I/O 작업을 하면 CPU는 대기 상태였던 transaction2 바로 실행 
  - ➡️ CPU 사용이 효율적 

### NonSerial Schedule 단점

- transaction들이 어떤 형태로 겹쳐서 실행되는지에 따라 이상한 결과가 나올 수 있다.
- CASE4: H의 최종 결과가 220만원 

### 고민거리 - NonSerial Schedule 사용하고 싶어..

- 성능 때문에 NonSerial Schedule 처리를 하고 싶다 
- 하지만 이상한 결과가 나오는 것은 싫다 

아이디어 등장 
- ➡️ serial schedule과 동일한(equivalent) nonserial schedule을 실행한다 
- ➡️ ``schedule이 동일하다``의미는 무엇인지 정의가 필요 

### Conflict of two operations

세 가지 조건을 모두 만족하면 conflict
1. 서로 다른 transaction 소속 
2. 같은 데이터 접근 
3. 최소 하나는 write operation

conflict 종류
![11.JPG](Image%2F11.JPG)
- r.w.c: read-write-conflict
- w.w.c: write-write-conflict

conflict operation은 순서가 바뀌면 결과도 바뀐다


### Conflict equivalent of two operations

두 조건이 모두 만족하면 conflict equivalent
1. 두 schedule은 같은 transaction들을 가진다.
2. 어떤 conflicting operations의 순서도 양쪽 schedule 모두 동일하다

![12.JPG](Image%2F12.JPG)
- sched.3와 sched.2는 conflict equivalent


### Conflict serializable

![12.JPG](Image%2F12.JPG)
- sched.3와 sched.2는 conflict equivalent
- sched.2는 Serial Schedule
- Serial Schedule과 conflict equivalent ➡️ Conflict serializable
- NonSerial Schedule(sched3) ➡️ Conflict serializable

### 고민거리 해결 

- 기존 고민거리 다시 정리
  - 성능 때문에 NonSerial Schedule 처리를 하고 싶다 
  - 하지만 이상한 결과가 나오는 것은 싫다 
- 고민거리 해결 
  - Serial Schedule과 Conflict equivalent 한 NonSerial Schedule을 찾으면 된다.
  - 즉 해당 schedule이 conflict serializable한지 확인 

### conflict serializable protocol 구현?

- 여러 transaction이 실행될 때 마다 해당 schedule이 conflict serializable인지 확인 
- 실제 RDBMS는 사용 ❌

실제 구현 
- 여러 transaction을 동시에 실행해도 schedule이 conflict serializable 하도록 보장하는 프로토콜 사용 

### concurrency control

concurrency control makes any schedule serializable
- Isolation 

isolation level provide relaxed isolation
- why?: 너무 엄격하면 성능 저하 
- then?: choose for yourself














