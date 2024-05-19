package OperatingSystem;

/**
 * 참고자료 : [쉬운코드] https://www.youtube.com/watch?v=ESXCSNGFVto&list=PLcXyemr8ZeoT-_8yBc_p_lVwRRqUaN8ET&index=14
 * 주제 : 자바로 살펴보는 프로그래밍 레벨에서 데드락
 */
public class Deadlock_Java {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread t1 = new Thread(()->{
           synchronized (lock1) {
               System.out.println("[t1] get lock1");
               synchronized (lock2){
                   System.out.println("[t1] get lock2");
               }
           }
        });

        Thread t2 = new Thread(()->{
            synchronized (lock2) {
                System.out.println("[t2] get lock2");
                synchronized (lock1){
                    System.out.println("[t2] get lock1");
                }
            }
        });

        t2.start();
        t1.start();


        /*
        각각 lock을 획득
        t1 -> lock1
        t2 -> lock2
        그런 다음

        t1 스레드를 보면 lock2를 취득 하려고 하고
        t2 스레드는 lock1을 취득 하려고 하는 상황

        -> 교착 상태
         */

    }
}
/* 교착 상태를 해결하는 방법

1. mutual exclusion이 꼭 필요한 상황인가 고민

2. t2 스레드를 lock2를 먼저 얻게 하는게 아니라 lock1을 먼저 얻게 해서
   circular wait를 방지한다.

       Thread t2 = new Thread(()->{
            synchronized (lock2) {
                System.out.println("[t2] get lock2");
                synchronized (lock1){
                    System.out.println("[t2] get lock1");
                }
            }
        });

       --> 변경

       Thread t2 = new Thread(()->{
            synchronized (lock1) {
                System.out.println("[t2] get lock2");
                synchronized (lock2){
                    System.out.println("[t2] get lock1");
                }
            }
        });

3. 꼭 중첩을 했어야 하나?
   현재 코드를 보면 t1스레드는 lock1을 얻고 lock2를 얻는다.
   t2스레드도 마찬가지로 lock2를 얻고 lock1을 얻는다.
   Hold and wait 형태

   중첩을 하지 않고
   synchronized (lock1) {
        System.out.println("[t1] get lock1");
   }
   synchronized (lock2){
         System.out.println("[t1] get lock2");
   }
   -> 이런 형태면 Hold and wait 상태X

이렇게 여러방법을 사용해서 데드락 발생 조건중 1가지를 없애면 데드락은 발생하지 않게 된다.



 */