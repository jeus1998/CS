package EasyCodeCS.OperatingSystem.Test;

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
