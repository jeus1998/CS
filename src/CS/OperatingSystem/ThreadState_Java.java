package CS.OperatingSystem;

/**
 * 참고 자료 : [쉬운 코드] https://www.youtube.com/watch?v=_dzRW48NB9M&list=PLcXyemr8ZeoT-_8yBc_p_lVwRRqUaN8ET&index=15
 * 주제 : Java thread의 상태 종류
 * 예제 코드 : Monitor_Java랑 똑같다.
 */
public class ThreadState_Java {
    public static void main(String[] args) throws Exception{

        BoundedBuffer buffer = new BoundedBuffer(); // condition variable, mutex 락 1개씩 가진다.

        Thread consumer = new Thread(()->{
            try { buffer.consume();}
            catch (Exception e){}
        });

        Thread producer = new Thread(()->{
            try { buffer.produce(100);}
            catch (Exception e){}
        });

        // 처음 consumer 스레드가 생성되면 state: NEW
        // 처음 producer 스레드가 생성되면 state: NEW

        consumer.start();   // consumer : RUNNABLE ->  람다식 실행
        producer.start();   // producer : RUNNABLE ->  람다식 실행

        consumer.join();    // 이미 TERMINATED 상태여서 join() 호출은 즉시 반환된다.
        producer.join();
        System.out.println("완료");

        /*  consumer 스레드 : C producer 스레드 : T                       #2
         public synchronized void produce(int item) throws Exception { // T 스레드는 C 스레드가 락을 반환하고 WAITING 상태
                 while (count == 5) {                                  // 그래서 락을 얻고 critical section에 진입
                      wait();                                          // 현재 버퍼에는 item이 1개도 없다 -> while(false)
                 }
                 buffer[count++] = item;                // T 스레드는 매개변수로 받아온 100을 버퍼에 넣는다.
                 notifyAll();                           // notifyAll()을 통해서 C 스레드가 WAITING -> BLOCKED 상태
                                      // 자바에서는 기본적으로 signal & continue 방식을 사용한다. 그래서 C 스레드가 BLOCKED
                                      // T 스레드는 produce 메서드를 탈출하면서 lock을 반환 이때 C 스레드는 다시 RUNNABLE
                                      // T 스레드는 TERMINATED
         }
         public void consume() throws Exception {
               int item = 0;               #1
               synchronized (this) {       // C 스레드가 먼저 시작했으니 성공적으로 모니터 lock을 얻고 critical section 진입
                   while (count == 0) {    // 아직 buffer에는 item이 1개도 없다
                       wait();             // C 스레드 RUNNABLE -> WAITING 본인이 가지고 있던 모니터 락을 반환
                   }                                 #3 Buffer에 아이템이 있으니까 item을 꺼낸다.
                   item = buffer[--count];
                   notifyAll();
               }
              System.out.println("Consume: " + item);  // 자신이 꺼낸 item을 출력하고 종료 C 스레드 TERMINATED
        }
         */
    }
}
/*
자바 스레드 상태 종류(6가지)
- NEW
- RUNNABLE
- BLOCKED
- WAITING
- TIMED_WAITING
- TERMINATED

#NEW
- 자바 스레드가 아직 시작하지 않은 상태

#TERMINATED
- 자바 스레드가 실행을 마치고 종료된 상태

#WAITING
- 다른 스레드를 기다리는 상태
- Object.wait() <- 모니터  자바의 모든 객체는 모니터를 가진다.
- Thread.join()

#TIMED_WAITING
- 제한 시간을 두고 다른 스레드를 기다리는 상태
- Object.wait with timeout -> Object.wait(time)
- Thread.join with timeout -> Thread.join(time) 시간을 파라미터로 전달하는 방식
- Thread.sleep(time)

#BLOCKED
- 모니터 락을 얻기 위해 기다리는 상태
- critical section으로 들어갈려고 모니터 락을 얻기 위해 기다리는 상태 entry queue에 들어있는 상태?

#RUNNABLE
- 실행 중인 상태
- 다른 리소스를 기다리는 상태도 포함 (CPU에서 ready queue에 들어있는 상황)
- IO 작업을 기다리는 상황
- 운영 제제의 state running과 비교하면 자바의 RUNNABLE은 상당히 포괄적인 의미이다.

Thread dump
- 실행 중인 자바 프로세스의 현재 상태를 담은 스냅샷
- 활용하면 어디가 병목인지 확인 가능하다

 */