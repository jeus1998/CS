# concurrency control 기초 recoverability

### 목차
- recoveralbe schedule
- cascadeless schedule
- strict schedule 

### unrecoverable schedule

![13.JPG](Image%2F13.JPG)
- schedule 내에서 commit된 transaction(1)이 rollback된 transaction(2)의 write 했었던 데이터를 읽는 경우 
- ➡️ unrecoverable schedule

unrecoverable schedule
- rollback 해도 이전 상태로 회복 불가능할 수 있다 
- 이런 schedule은 DBMS가 허용하면 안된다 

### recoverable schedule

그러면 어떤 schedule이 recoverable 한가?

![14.JPG](Image%2F14.JPG)
- 트랜잭션1의 read(H)는 트랜잭션2의 write를 의존하고 있다.
- schedule 내에서 그 어떤 transaction도 자신이 읽은 데이터를 write한 transaction이 먼저 
  commit/rollback 전까지는 commit 하지 않는 경우
- ➡️ recoverable schedule

recoverable schedule
- rollback 할 때 이전 상태로 온전히 돌아갈 수 있다
- DBMS는 이런 schedule만 허용해야 한다 

### cascadeless schedule

![15.JPG](Image%2F15.JPG)
- 트랜잭션1의 read(H)는 트랜잭션2의 write를 의존하고 있다.
- 트랜잭션1이 rollback(abort)하면 트랜잭션2 또한 rollback(abort)한다 
- ➡️ cascading rollback 

cascading rollback
- 여러 transaction의 rollback이 연쇄적으로 일어나면 처리하는 비용이 많이 든다 

어떻게 해결할까? - cascading rollback
- 데이터를 write한 transaction이 commit/rollback 한 뒤에 데이터를 읽는 schedule만 허용하자

![16.JPG](Image%2F16.JPG)

- 트랜잭션2번이 만약 rollback을 하면 트랜잭션1은 트랜잭션2에 의존하지 않기 때문에 
  정상 처리(commit)을 해도 된다.
- ➡️ cascadeless schedule

cascadeless schedule
- schedule 내에서 어떤 transacion도 commit 되지 않은 transaction들이 write한 데이터는 읽지 않는 경우
- avoid cascading rollback

### strict schedule

또 다른 예제 
![17.JPG](Image%2F17.JPG)

![18.JPG](Image%2F18.JPG)

strict schedule
- schedule 내에서 어떤 transaction도 commit 되지 않은 transaction들이 write한 데이터는
  쓰지도 읽지도 않는 경우 

![19.JPG](Image%2F19.JPG)
- rollback 할 때 recovery가 쉽다 
- transaction 이전 상태로 돌려놓기만 하면 된다 

### 최종 정리 

- unrecoverable schedule
  - DBMS가 허용 하면 ❌ 
- recoverable schedule
  - DBMS가 허용 해도 ⭕️

concurrency control provides serializability & recoverability 