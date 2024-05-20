package OperatingSystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 참고자료 : [쉬운코드] https://www.youtube.com/watch?v=B4Of4UgLfWc&list=PLcXyemr8ZeoT-_8yBc_p_lVwRRqUaN8ET&index=19
 * 주제 : 스레드 풀 팁 & 예제
 * newFixedThreadPool(int nThreads); -> 제한이 없는 스레드 풀 큐를 생성 -> 상황에 따라 메모리 고갈
 */
public class ThreadPoolTip {
    public static void main(String[] args) {

        // 큐의 제한이 없다. Integer.MAX_VALUE -> 제한이 없다.
        // 요청을 처리하지 못하고 쌓이면 20억개가 넘는 요청이 스레드 풀 큐에 쌓인다.
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        /*
           newFixedThreadPool(int nThreads) method
           public static ExecutorService newFixedThreadPool(int nThreads) {
                return new ThreadPoolExecutor(nThreads, nThreads,
                                       0L, TimeUnit.MILLISECONDS,
                                       new LinkedBlockingQueue<Runnable>());
          }

          LinkedBlockingQueue 생성자
          public LinkedBlockingQueue() {
                this(Integer.MAX_VALUE); -> LinkedBlockingQueue(int capacity);
          }

          LinkedBlockingQueue(int capacity) 생성자 <- 스레드 풀 큐 사이즈
          public LinkedBlockingQueue(int capacity) {
          if (capacity <= 0) throw new IllegalArgumentException();
             this.capacity = capacity;
             last = head = new Node<E>(null);
          }

          */


    }
}
/*
Thread pool 사용 팁

1. 스레드 풀에 몇 개의 스레드를 만들어 두는 게 적절한가?
CPU 코어 개수와 task의 성향에 따라 다르다.

case1 : CPU-bound task -> 코어 개수 만큼 혹은 그 보다 몇 개 더 많은 정도
case2 : I/O-bound task -> 코어 개수보다 1.5배? 2배? 3배? 경험적으로 찾야아함

2. 스레드 풀에서 실행될 task 개수에 제한이 없다면?
스레드 풀의 큐가 사이즈 제한이 있는지 꼭 확인할 것

요청이 계속 들어오는데 스레드 풀 queue의 제한이 없다면
해당 queue에 요청이 계속 쌓인다. 요청을 버리더라도 시스템이 다운되지 않도록 해야한다.

CAUTION! 자바의 Executors class

static 메서드로 다양한 형태의 스레드 풀을 제공

ExecutorService threadPool = Executors.newFixedThreadPool(10);
threadPool.submit(task1);
threadPool.submit(task2);

이렇게 생성된 threadPool의 큐 사이즈는?

Integer.MAX_VALUE -> 제한이 없다.

-> Executors.newFixedThreadPool() 안심하고 생성하지 말라

정리
사이즈 제한이 없는 큐는 상황에 따라서는 메모리를 고갈시키는 잠재적인 위험 요인이 될 수 있다.

pool 이라는 개념은 스레드에만 쓰이는 것은 아니다.
- connection pool
- process pool
 */