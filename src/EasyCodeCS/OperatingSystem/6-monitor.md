# 모니터 (monitor)

### 모니터(monitor)?

- mutual exclusion 보장
- 조건에 따라 스레드가 대기(waiting) 상태로 전환 가능

### 모니터는 언제 사용하는가?

- 한번에 하나의 스레드만 실행돼야 할 때 
- 여러 스레드와 협업(cooperation)이 필요할 때 

### 모니터의 구성 요소
- mutex
  - critical section 에서 mutual exclusion을 보장하는 장치 
  - critical section 에 진입하려면 mutex lock을 취득해야 함 
  - mutex lock을 취득하지 못한 스레드는 큐에 들어간 후 대기(waiting)상태로 전환 
  - mutex lock을 쥔 스레드가 lock을 반환하면 락을 기다리며 큐에 대기 상태로 있던 스레드 중 하나가 실행 
- condition variable(s)
  - waiting queue를 가짐 
    - 조건이 충족되길 기다리는 스레드들이 대기 상태로 머무르는 곳 

### condition variable 주요 동작(operation)

- wait
  - thread가 자기 자신을 condition variable의 waiting queue에 넣고 대기 상태로 전환 
- signal 
  - waiting queue에서 대기중인 스레드 중 하나를 깨움 
- broadcast
  - waiting queue에서 대기중인 스레드 전부를 깨움 

### 모니터 뼈대 살펴보기 

```c
acquire(m);                             // 모니터의 락 취득 (락을 취득한 스레드가 없다면) 
                                        // 어떤 스레드가 락을 취득해서 사용 중이라면 해당 스레드는 entry queue
 
--> critical section 시작  
 
while(!p){                              // 조건 확인
    wait(m,cv);                         // 조건 충족 안되면 waiting 즉 모니터의 waiting queue에 들어간다.
}                        // 파라미터로 m(뮤텍스락)이 전달되는 이유: waiting queue에서 언제 깨어날지 모르는데 락을 반환하자
                                                                               
... 이런 저런 코드들 

signal(cv2); -- OR -- broadcast(cv2);   // cv2, cv가 같을 수도 있음

--> critical section 끝 

release(m);                             // 모니터의 락 반환 
```

모니터에는 2개의 큐가 존재한다.
- entry queue: critical section에 진입을 기다리는 큐(mutex 락 취득을 기다리는 공간) 
- waiting queue: 조건이 충족되길 기다리는 기다리는 큐(condition variable에 의해 관리) 

### 예제로 살펴보는 모니터 

bounded producer/consumer problem
```text

p(producer)                            c(consumer)
             buffer [][][][][][][][]      
p                                      c

p: producer - item 생산 -> buffer에 넣는다.
c: consumer - item 소비 -> buffer에서 item을 꺼내서 사용(작업) 


producer 입장의 문제점 

buffer가 만약 꽉 차서 생산한 item을 못 넣는다면 
이 문제를 방지하기 위해서 producer는 계속 buffer의 남은 공간을 확인 

consumer 입장의 문제점 

buffer의 item이 없어서 item을 꺼내지 못한다면?
이 문제를 방지하기 위해서 consumer는 계속 buffer에 item이 있는지 확인 

이런 문제를 모니터를 통해서 해결 가능하다.
```

![13.jpg](Image%2F13.jpg)

### 자바에서 모니터란?

- 자바에서 모든 객체는 내부적으로 모니터를 가진다 
- 모니터의 mutual exclusion 기능은 synchronized 키워드로 사용된다 
- 자바의 모니터는 condition variable를 하나만 가진다 
- 자바 모니터의 세 가지 동작 
  - wait = wait
  - signal = notify
  - broadcast = notifyAll

### bounded producer/consumer problem with Java

```java
public class MonitorTest {
    public static void main(String[] args) throws InterruptedException{
        BoundedBuffer buffer = new BoundedBuffer();

        Thread consumer = new Thread(()->{
            try {
                buffer.consume();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread producer = new Thread(()->{
            try {
                buffer.produce(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 각각의 람다 표현식 시작
        consumer.start();
        producer.start();

        consumer.join();  // 소비자 스레드가 종료될 때까지 main 스레드 대기
        producer.join();  // 생산자 스레드가 종료될 때까지 main 스레드 대기
        System.out.println("완료");

    }
}

class BoundedBuffer {
    private final int[] buffer = new int[5];
    private int count = 0;

    public synchronized void produce(int item) throws InterruptedException {
        while (count == 5) {
            wait();
        }
        buffer[count++] = item;
        System.out.println("Produce done");
        notifyAll();
    }

    public void consume() throws InterruptedException {
        int item = 0;
        synchronized (this) {
            while (count == 0) {
                System.out.println("Consumer wait");
                wait();
            }
            item = buffer[--count];
            notifyAll();
        }
        System.out.println("Consume: " + item);
    }
}
```

### 자바 동기화

- condition variable이 2개가 필요하다면? 
  - 직접 구현
  - [예제](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/concurrent/locks/Condition.html)
- java.util.concurrent
  - 동기화 기능이 탑재된 여러 클래스들이 있으니 참고 





