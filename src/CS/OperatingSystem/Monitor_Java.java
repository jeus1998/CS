package CS.OperatingSystem;

/**
 * 주제 : 자바에게 모니터란?
 * 참고 자료 : [쉬운코드] https://www.youtube.com/watch?v=Dms1oBmRAlo&list=PLcXyemr8ZeoT-_8yBc_p_lVwRRqUaN8ET&index=11
 * bounded producer/consumer problem with java
 */
public class Monitor_Java {
    public static void main(String[] args) throws Exception{
        BoundedBuffer buffer = new BoundedBuffer();
        Thread consumer = new Thread(() -> {
            try {
                buffer.consume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread producer = new Thread(() -> {
            try {
                buffer.produce(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        consumer.start(); // 둘 중에 누가 먼저 시작하는지는 모른다. 하지만 mutual exclusion은 확실하다.
        producer.start();

        consumer.join();
        producer.join();
        System.out.println("완료");
    }
}

class BoundedBuffer {
    private final int[] buffer = new int[5];
    private int count = 0;

    public synchronized void produce(int item) throws Exception {
        while (count == 5) {
            wait();
        }
        buffer[count++] = item;
        notifyAll();
    }

    public void consume() throws Exception {
        int item = 0;
        synchronized (this) { // synchronized 블럭을 사용하면 parameter로 lock을 넘겨주는데 이때 본인 객체 자체를 넘겨준다.
            while (count == 0) {
                wait();
            }
            item = buffer[--count];
            notifyAll();
        }
        System.out.println("Consume: " + item);
    }
}



/*
모니터 <- 프로그래밍 언어 레벨에서 지원
모니터 자체를 구현해서 사용할 일은 거의 없고 지원하는 모니터를 사용하면 된다.

자바에서의 모니터
- 모든 객체는 내부적으로 모니터를 가진다.
- 모니터의 mutual exclusion 기능은 synchronized 키워드로 사용한다.
- 자바 모니터는 condition variable를 하나만 가진다.

자바 모니터의 세 가지 동작
- wait
- notify == signal
- notifyAll == broadcast


자바 모니터를 사용할 때 두 가지 이상의 condition variable이 필요하다면 따로 구현이 필요하다.
java.util.concurrent에는 동기화 기능이 탑재된 여러 클래스들이 있으니 참고하면 된다.

 */