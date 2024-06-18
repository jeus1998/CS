# 동기화(Synchronization), 경쟁 조건(race condition), 임계 영역(critical section)

### 동기화가 없으면 생길 수 있는 일 

- 하나의 객체를 두 개의 스레드가 접근할 때 생긴 일 
- 상한 귤을 골라내자 -> 스레드 2개를 사용해서 
```java
@RequiredArgsConstructor
public class Main{
    private final Counter badCounter;
    public static void func(int [] gyulBox){
       for(int cur : gyulBox) {
           if (cur == -1){
               bacCounter.increment();
           }
       }
    }
}
@Component 
public class Counter{
    private int state = 0;
    public void increment(){
        state++;
    }
    public int get(){
        return state;
    }
}
```

시나리오 when given then
```text
// given 
Counter는 싱글톤 객체로서 등록 
자바의 힙 메모리 영역에 들어있다. 
CPU는 싱글 코어로 2개의 스레드가 있다.(T1, T2)

T1 스레드와 T2 스레드는 각각 귤 박스를 할당 받고 
Counter를 통해서 상한 귤을 골라낸다. 
이때 모두 Main Class 코드를 기반으로 동작을 한다. 

 T1 스레드는 상한 귤이 2개 
 T2 스레드는 상한 귤이 5개 
 
// when 

count 

// then 

우리의 예상은 Counter의 state가 7이다. 
하지만 실제 동작은 항상 7을 보장하지 않는다(동기화 없이는)
```

Counter 클래스의 increment() - CPU 레벨에서의 동작 
```text
increment() {state++;}

LOAD state to R1
R1 = R1 + 1
STORE R1 to state 
```

T1 T2 동기화 문제 시나리오 
```text

T1이 먼저 increment() 실행

LOAD state(0) to R1
R1(1) = R1(0) + 1 

----------> T1에서 T2로 컨텍스트 스위칭 발생 

T2 또한 상한 귤을 발견하고 increment() 실행 

LOAD state(0) to R1
R1(1) = R1(0) + 1
STORE R1(1) to state  state: 0 -> 1

----------> T2에서 T1로 컨텍스트 스위칭 발생 

종료 시점에서 다시 시작 
STORE R1(1) to state  state: 1 -> 1

T1,T2 스레드 종료 

우리는 state 값이 2를 기대하지만 
실제 state 값은 1이다. 
```

### race condition(경쟁 조건)

- 여러 프로세스/스레드가 동시에 같은 데이터를 조작할 때 
- 타이밍이나 접근 순서에 따라 결과가 달라질 수 있는 상황 

### synchronization(동기화)

- 여러 프로세스/스레드를 동시에 실행해도 공유 데이터의 일관성을 유지하는 것 

### 어떻게 동기화 시킬 것인가? 

```text
increment() 메서드를 실행할 때 
딱 1개의 스레드만 실행하도록 한다. 
다른 스레드들은 기다리도록 만든다. 
```

### critical section(임계 영역)

- 공유 데이터의 일관성을 보장하기 위해 하나의 프로세스/스레드만 진입해서 실행 가능한 영역 

### critical section problem(synchronization problem) 해결하는 뼈대 

```text
do{
    entry section
         critical section
    exit section
         remainder section
} while(true)
```

### critical section problem의 해결책이 되기 위한 조건 

1. mutual exclusion (상호 배제)
2. progress (진행)
3. bounded waiting (한정된 대기)

- mutual exclusion
  - 한 번에 하나의 프로세스/스레드만 critical section 진입이 가능해야 한다. 
- progress
  - critical section을 진입한 프로세스/스레드가 없고 
  - critical section에 진입하려는 프로세스/스레드가 있다면 진행해야 한다. 
- bounded waiting
  - critical section에 진입하려는 프로세스/스레드가 무한정 대기해서는 안 된다.

### thread unsafe

```text
처음 예시로 만들었던 상한 귤 찾기 예제는 thread unsafe 하다
멀티스레딩 환경에서 사용하면 race condition 존재 

우리가 custom한 클래스가 아닌 언어에서 제공하는 클래스나, 구현체 등등.. 이 thread unsafe 한 경우도 있다.
자바에서 기본으로 제공하는 클래스가 thread safe 한지 꼭 확인하고 사용하자 
```
