
## DataBase Anormaly(이상 현상 )

### Dirty-Read
- commit 되지 않은 데이터를 읽음 
![1.jpg](MyImage%2F1.jpg)

### Non-Repeatable-Read
![2.jpg](MyImage%2F2.jpg)

### Phantom-Read
- 없던 데이터가 보임 
![3.jpg](MyImage%2F3.jpg)

PhantomRead 현상을 막을면 어떤 격리 레벨이 필요할까?
- row 레벨의 락으로는 팬텀 리드 현상을 막기 힘들다.
- 테이블 전체에 락으로 팬텀 리드를 막는다. 
- 테이블 전체에 락을 거는 격리 레벨은 직렬화 수준 격리 레벨이다.
- 하지만 실무에서는 한 트랜잭션에서 동일한 데이터에 대해서 2번 조회 하는 경우는 거의 없다.
- 그래서 굳이 직렬화 수준 격리 레벨을 선택할 이유는 없어 보인다. 
- 만약 이런 팬텀 리드 현상이 생기는 애플리케이션 특성이라면 직렬화 수준 레벨을 고려해 봐야 할듯한다.

### Isolation level (표준 SQL 기준)

```text
Dirty read, Non-repeatable read, Phantom read
이런 이상한 현상들을 모두 발생하지 않게 만들 수 있지만
그러면 제약사항이 많아져서 동시 처리 가능한 트랜잭션 수가 줄어들어 결국 
DB의 전체 처리량이 하락하게 된다 
```

해결방안 
```text
일부 이상한 현상은 허용하는 몇 가지 level을 만들어서 
사용자가 필요에 따라 적절하게 선택할 수 있도록 하자
-> Isolation level
```

Isolation level
![4.JPG](MyImage%2F4.JPG)

- Serializable: 위 세 가지 현상 뿐만 아니라 아예 이상한 현상 자체가 발생하지 않는 level을 의미 
- 애플리케이션 설계자는 isolation level을 통해 전체 처리량과 데이터 일관성 사이에서 어느 정도 거래를 할 수 있다.
- 해당 Isolation level은 1992년 11월에 발표된 SQL 표준에서 정의된 내용 

## 표준 isolation level 비판 논문 등장 

1. 세 가지 이상 현상의 정의가 모호하다 
2. 이상 현상은 세 가지 외에도 더 있다 
3. 상업적인 DBMS에서 사용하는 방법을 반영해서 isolation level을 구분하지 않았다

### Lost Update & Dirty Write  

![5.jpg](MyImage%2F5.jpg)

DirtyWrite
- commit 안된 데이터를 write함 
- rollback 시 정상적인 recovery는 매우 중요하기 때문에 모든 isolation level에서 dirty write를 허용하면 안된다

### Dirty read 확장판 
![6.jpg](MyImage%2F6.jpg)


### Read skew
![7.jpg](MyImage%2F7.jpg)


### SNAPSHOT ISOLATION

![8.jpg](MyImage%2F8.jpg)

SNAPSHOT ISOLATION 특징
- 트랜잭션 시작 전에 commit 된 데이터만 보임 
- First-committer win 
- MVCC 종류 
