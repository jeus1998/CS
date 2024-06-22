# 스레드 풀(thread pool)

### Thread per request model 

```text
만약 thread per request model 동작 방식이 
서버에 들어오는 요청마다 스레드를 새로 만들어서 처리하고 
처리가 끝난 스레드는 버리는 식으로 동작한다면 어떤 문제점이 있을까?

1. 스레드 생성에 소요되는 시간 때문에 
-> 요청 처리가 더 오래 걸림 

2. 처리 속도보다 더 빠르게 요청이 늘어나면 
-> 스레드가 계속 생성(스레드 수 증가) 
-> 컨텍스트 스위칭이 더 자주 발생 
-> CPU 오버헤드 증가로 CPU time 낭비 
-> 어느 순간 서버 전체가 응답 불가능 상태에 빠짐 

3. 처리 속도보다 더 빠르게 요청이 늘어나면 
-> 스레드가 계속 생성(스레드 수 증가) 
-> 메모리가 점점 고갈됨 

Thread pool

미리 스레드를 여러 개 만들어 놓고 재사용 
-> 스레드 생성 시간 절약 

제한된 개수의 스레드를 운용 
-> 스레드가 무제한으로 생성되는 것을 방지 

Thread pool 사례: 여러 작업을 동시에 처리해야 할 때 
thread per request model
task를 subtask로 나뉘어서 동시에 처리 
순서 상관없이 동시 실행이 가능한 task 처리 

Thread pool 참고사항 

스레드 풀에 몇 개의 스레드를 만들어 두는 게 적절한가? 
-> CPU의 코어 개수와 task 성향에 따라 다름 

CPU-bound task
코어 개수 만큼 혹은 그 보다 몇 개 더 많은 정도 

I/O-bound task
코어 개수보다 1.5배? 2배? 3배? 경험적으로 찾아야 함 

스레드 풀에서 실행될 task 개수에 제한이 없다면
-> 스레드 풀의 큐가 사이즈 제한이 있는지 꼭 확인할 것!
```

자바의 Executors 클래스 
- static 메서드로 다양한 형태의 스레드 풀을 제공 
```java
public class ThreadPoolTest {
    public static void main(String[] args) {
        // thread pool 생성, 10은 스레드 개수
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        /*
          public static ExecutorService newFixedThreadPool(int nThreads) {
                    return new ThreadPoolExecutor(nThreads, nThreads,
                                            0L, TimeUnit.MILLISECONDS,
                                            new LinkedBlockingQueue<Runnable>());
          }
          LinkedBlockingQueue 생성자 큐 크기를 Integer.MAX_VALUE 즉 제한이 없다.
          public LinkedBlockingQueue() {
                 this(Integer.MAX_VALUE);
          }
          // 기본 생성자는 내부에서 int 타입 파라미터(Integer.MAX_VALUE)를 가지는 생성자 호출
          public LinkedBlockingQueue(int capacity) {
                if (capacity <= 0) throw new IllegalArgumentException();
                this.capacity = capacity;
                last = head = new Node<E>(null);
          }
          // 정리 사이즈 제한이 없는 큐가 생성
         */
        
        // 큐 사이즈를 제한하는 스레드 풀 예제 
        int corePoolSize = 10;
        int maximumPoolSize = 10;
        long keepAliveTime = 0L;
        int queueCapacity = 100; // 큐의 크기를 100으로 제한
        
        ExecutorService threadPool2 = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueCapacity)
        );
    }
}
```
- `` ExecutorService threadPool = Executors.newFixedThreadPool(10);``
  - 큐 사이즈 Integer.MAX_VALUE 
- 사이즈 제한이 없는 큐는 상황에 따라서 메모리를 고갈시키는 잠재적인 위험 요인이 될 수 있다. 
- 요청을 버리더라도 서버를 지키는 게 더 중요하다. 