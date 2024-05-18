package OperatingSystem;

/**
 * [쉬운 코드] : https://www.youtube.com/watch?v=vp0Gckz3z64&list=PLcXyemr8ZeoT-_8yBc_p_lVwRRqUaN8ET&index=9
 * 주제 : 동기화, 경쟁 조건, 임계 영역
 */
public class Synchronization_RaceCondition_CriticalSection {
    public static void main(String[] args) {
        /*
         for(귤 in 귤박스){
            if(귤 상태 is 불량){
                 불량귤카운터.increment();
                 }
         }
         */
    }
    static class Counter{
        private int state = 0;
        public void increment() {
            state++;
        }
        public int get(){
            return state;
        }
    }
}

/*
목표 : 왜 동기화(synchronization)이 중요한지 이해

예시로 이해하는 동기화의 중요성!

하나의 객체를 두 개의 스레드가 접근할 때 생긴 일

위에 코드를 예시로 들어보자
귤박스 for문을 돌면서 귤이 불량이면 increment() 메서드로 state를 증가 시킨다.

이 작업을 2개의 스레드가 작업을 한다.

CPU(싱글 코어) T1, T2 <- 스레드
T1, T2 는 같은 프로세스에 있는 스레드니깐 heap 영역을 공유한다. -> increment()를 공유

귤박스1(T1담당) -> 불량 귤 1개 귤박스2(T2담당) -> 불량 귤 1개
우리가 기대하는 state = 결과(불량귤)는 2이다.

위 코드는 프로그램 언어 레벨이고 cpu 레벨에서의 진행을 한번 보자 state+++;

LOAD state to R1(레지스터)
R1 = R1 + 1
STORE R1 to state

cpu에서는 이렇게 메모리에서 값을 가져와서 레지스터에 저장을하고 연산(ALU)을 하고 다시 메모리에 로드를 한다.

이제 T1,T2가 진행을 해보자

T1
LOAD state to R1(레지스터)  이때 state는 0이다. R1에 0이 들어간다.
R1 = R1 + 1    R1 = 0 + 1 R1은 1을 가진다.
----------------------  <- T2로 스레드 컨텍스트 스위칭이 발생!! (이유는 예) cpu 점유 시간이 끝나서)
그럼 T2 작업을 시작한다.
LOAD state to R1(레지스터)  T1 스레드가 state에 STORE 작업을 하지 않고 스위칭이 일어나서 이때 state는 0이다.
R1= R1 + 1  R1 = 1
STORE R1 to state state에 1 저장 T2는 중간에 스위칭이 발생하지 않고 잘 동작
----------------------- <- T1으로 컨텍스트 스위칭
이전에 하던 작업을 마무리 한다.
R1에는 그대로 1이 들어있다.
STORE R1 to state state 1 저장

결과를 get()해서 출력하면 1이 나온다 (예상 결과는 2) 동기화의 중요성!!!!

지금까지 진행했던 결과는 동기화가 없으면 생길 수 있는 일

race condition(경쟁 조건)
- 여러 프로세스/스레드가 동시에 같은 데이터를 조작할 때 타이밍이나 접근 순서에 따라 결과가 달라질 수 있는 상황

synchronization(동기화)
- 여러 프로세스/스레드를 동시에 실행해도 공유 데이터의 일관성을 유지하는 것

-------------------------
프로그램 언어 level
state++;

cpu level
LOAD state to R1(레지스터)
R1 = R1 + 1
STORE R1 to state
--------------------------

어떻게 동기화 시킬 것인가?
1. LOAD 부터 STORE 까지의 과정에서는 컨텍스트 스위칭을 하지 않게 한다? -> 만약 멀티 코어에서 멀티 스레딩 상황이라면 똑같다.
ex) T1이 STORE 하기 전에 T2가 LOAD
2. incremet() 메서드 자체를 한번에 한 스레드만 사용하게 한다. 사용중이라면 사용x 사용중인 스레드가 실행이 끝나면 사용 가능
-> 이런 방식이라면 멀티 코어든 싱글 코어든 문제가 없다.

critical section(임계 영역)
공유 데이터의 일관성을 보장하기 위해 하나의 프로세스/스레드만 진입해서 실행 가능한 영역

citical section problem의 해결책이 되기 위한 조건들
1. mutual exclusion(상호 배제) -> 한개의 프로세스/스레드만 critical section에서 실행 가능하다
2. progress(진행) -> 현재 critical section에서 어떤 프로세스/스레도도 실행하지 않으면 critical section에서 진행을 원하는 프로세스/스레드
가 있다면 실행 하도록 해야한다.
3. bounded waiting(한정된 대기) -> 어떤 프로세스/스레드가 critical section에 들어가지 못하고 무한정 대기하는 상황은 없어야 한다.

지금까지 예시로 설명한 귤박스 불량시 update하는 increment()는 Thread-unsafe하다. (race condition)이 존재

프로그래밍 언어 레벨에서 제공하는 기본적인 클래스에서도 Thread-unsafe 하지 않는 이슈가 있을 수 있다.
자바도 마찬가지이다. 문서를 잘 읽어보자
ex) SimpleDateFormat <- thread unsafe class
Date formats are not synchronized
It is recommend to create separate format instances for each thread
If multiple threads access a format concurrently, it must be synchronized externally
 */
