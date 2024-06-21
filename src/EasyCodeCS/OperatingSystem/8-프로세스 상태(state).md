# OS에서 프로세스 상태

### 프로세스 상태

![17.png](Image%2F17.png)
- new ➡️ ready: 대부분의 os는 ready(state)부터 시작 즉 new 생략 
- ready ➡️ running: 프로세스/스레드가 ready state(CPU 할당을 기다리는)에서 running(CPU에서 작업)상태
- running ➡️ ready: CPU 점유시간(time slice)소비를 다 해서 
- running ➡️ waiting: I/O 작업 or Critical Section에 들어가기 위한 리소스 할당 대기 
- waiting ➡️ ready: I/O 작업이 끝나거나 or Critical Section에 들어가기 위한 리소스를 가졌다면 
  - Critical Section에 들어가기 위한 리소스를 가졌다면 바로 running 상태가 되는게 아니다. 
- running ➡️ terminated: 프로세스/스레드가 종료 

### Java Thread 상태 종류 

- NEW
  - 자바 스레드가 아직 시작하지 않은 상태 
- RUNNABLE
  - 실행 중인 상태 
  - 다른 리소스를 기다리는 상태도 포함 
  - CPU를 기다리는 상태도 포함 즉 OS 레벨의 state 보다 더 넓은 범위의 개념 
- BLOCKED
  - 모니터 락을 얻기 위해 기다리는 상태 
  - critical section 으로 들어가려고 모니터 락을 얻기 위해 기다리는 상태 
- WAITING
  - 다른 스레드를 기다리는 상태 
  - Object.wait
  - Thread.join
- TIMED_WAITING
  - 제한 시간을 두고 다른 스레드를 기다리는 상태 
  - Object.wait with timeout
  - Thread.join with timeout
  - Thread.sleep 
- TERMINATED
  - 실행을 마치고 종료된 상태 

### Java Thread 상태 변화 

![18.png](Image%2F18.png)

시뮬레이션 
```text

consumer: c producer: p 

BoundedBuffer buffer = new BoundedBuffer(); 
- BoundedBuffer 객체 buffer : 모니터를 가진다.(1개의 Mutex 락, 1개의 ConditionVariable)

Thread consumer = new Thread(()->{
    buffer.consumer();
});
// producer 생략 
-> c,p 스레드 state: NEW 

consumer.start();
producer.start();

-> c,p 스레드 state: RUNNABLE

c 스레드는 consume 메서드에서 락을 획득 하지만 p 스레드가 아직 item 추가를 하지 않았기 때문에 

while(count == 0) 조건이 true가 되어 wait(); 
-> c 스레드 state: WAITING, mutex 락 반납 

p 스레드는 producer 메서드에서 락을 획득 buffer에 item을 넣고 notifyAll();

-> c 스레드는 깨어난다. 

자바는 signal and continue 방식이다. 

그래서 p 스레드가 c 스레드를 깨웠지만 p 스레드가 synchronized 블록 안에서 계속 활동하며 
락을 반납을 하지 않는다.
그럼 c 스레드는 WAITING -> BLOCKED 상태로 바뀐다. 

p 스레드가 synchronized 블록 탈출, 모니터 락 반납 
그럼 c 스레드는 BLOCKED -> RUNNABLE 
c 스레드는 락을 획득하고 buffer에서 item을 갖고 온다. 

item 출력을 하고 TERMINATED

main 스레드는 consumer.join, producer.join을 통해서 
해당 스레드들이 TERMINATED 될 때까지 기다린다. 

모두 TERMINATED 되면 완료를 출력하고 main 스레드 또한 종료 
```
- join()을 호출하는 스레드가 해당 스레드가 TERMINATED 될 때까지 기다린다. 

### Java Thread dump

- 실행 중인 자바 프로세스의 현재 상태를 담은 스냅샷 
- 만약 스레드들이 데드락 상태라면 어디가 병목 상태인지 터미널에서 Thread dump를 실행하여 스레드 상태를 확인한다. 
