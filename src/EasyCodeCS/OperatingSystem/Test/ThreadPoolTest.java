package EasyCodeCS.OperatingSystem.Test;

import java.util.concurrent.*;

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
