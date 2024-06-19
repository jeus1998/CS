package EasyCodeCS.OperatingSystem.Test;

/**
 * 데드락 예제
 */
public class DeadlockTest {
    public static void main(String[] args) throws InterruptedException{
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread t1 = new Thread(()->{
                    synchronized (lock1){
                        System.out.println("[t1] get lock1");
                    }
                    synchronized (lock2){
                        System.out.println("[t1] get lock2");
                    }
                });

                Thread t2 = new Thread(()->{
                   synchronized (lock2){
                       System.out.println("[t2] get lock2");
                   }
                  synchronized (lock1){
                      System.out.println("[t2] get lock1");
                  }
               });

       t1.start();
       t2.start();

       t1.join();
       t2.join();
       System.out.println("완료");
    }
}
