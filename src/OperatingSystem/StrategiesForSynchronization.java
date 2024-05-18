package OperatingSystem;

/**
 * [쉬운 코드] : https://www.youtube.com/watch?v=gTkvX2Awj6g&list=PLcXyemr8ZeoT-_8yBc_p_lVwRRqUaN8ET&index=10
 * 주제 : 동기화를 위한 여러가지 전략 스핀락(spinlock) 뮤텍스(mutex) 세마포(semaphore)
 */
public class StrategiesForSynchronization { }
/*
목표 : 동기화(synchronization)를 위한 여러 전략과 각각의 차이를 이해

개념 정리(복습)
race condition(경쟁 조건)
- 여러 프로세스/스레드가 동시에 같은 데이터를 조작할 때 타이밍이나 접근 순서에 따라 결과가 달라질 수 있는 상황

synchronization(동기화)
- 여러 프로세스/스레드를 동시에 실행해도 공유 데이터의 일관성을 유지하는 것

critical section(임계 영역)
- 공유 데이터의 일관성을 보장하기 위해 하나의/프로세스만 진입해서 실행 가능한 영역

하나의 프로세스/스레드만 진입해서 실행 -> mutual exclusion
어떻게 mutual exclusion을 보장할 수 있을까?
-> 락(lock)을 사용하자

do {
    acquire lock
        critical section
    release lock
        remainder section
}while(true)

-----------------------------------------------------------------------------------------------------------
스핀락(spinlock) feat CPU의 도움

volatile int lock = 0; // global

int TestAndSet(int* lockPtr){   // 무조건 lock을 1로 바꿔주는 함수 return 값은 이전 lock이 0이면 0 1이면 1 반환
    int oldLock = *lockPtr;
    *lockPtr = 1;
    return oldLock;
}

void critical(){
    while(test_and_set(&lock)==1);
    ... critical section
    lock = 0;
}

T1, T2 스레드가 있다.

T1 스레드 먼저 시작
현재 lock = 0

일단 test_and_set에서는 반환값이 0이지만 test_and_set 안에서 lock을 1로 바꿔준다.

while(test_and_set(&lock)==1) 반환값이 0이기 때문에 0!=1 while문은 종료되고
critical section 진행

T2 스레드도 시작
현재 lock은 1이다. 그래서 test_and_set은 1을 반환한다. test_and_set 안에서 lock을 1로 바꿔준다. (기존 lock도 1이다.)

while(test_and_set(&lock)==1) 반환값이 1이기 때문에 1==1 while문은 계속 반복

T1 스레드가 critical section에서 할일이 끝났다.
그럼
lock = 0; 동작하여 lock = 0 으로 바뀐다.

그럼 T2 스레드는 계속해서 while문 안에서 test_and_set을 반환하는 작업을 반복하고 있다가
while문의 조건이 0!=1이기 때문에 while문을 종료하고 critical section을 시작한다
물론 이때도 test_and_set에서 lock=1로 바꿔준다.

T2 스레드도 critical section을 종료하면 lock = 0으로 바꾼다.

정리
T1, T2 스레드가 TestAndSet을 활용해서 critical section을 동시에 사용하지 않게 한다.

하지만 의문점이 있다.
멀티스레딩 환경이라면
동시에 T1, T2가 시작이 가능한데 그럼 while문 조건은 둘다 false가 되어서 두 스레드 모두 critical section으로 진입한다.

하지만 그렇지 않다. TestAndSet에는 숨겨진 비밀이 있다. -> CPU의 도움을 받는다.

TestAndSet은 CPU atomic 명령어이다.

atomic 명령어의 특징
- 실행 중간에 간섭받거나 중단되지 않는다.
- 같은 메모리 영역에 대해 동시에 실행되지 않는다.

자 그래서 멀티 스레딩 환경에서 T1,T2 스레드가 동시에 TestAndSet을 실행 하려고 해도
atomic 명령어 이기 때문에 CPU레벨에서 동시에 실행하지 못하게 한다. 딱 1개만 실행 하도록 한다.

이런 형태의 락을 스핀락(splinlock)이라고 한다.
스핀락(spinlock) : 락을 가질 수 있을 때 까지 반복해서 시도
하지만 이런 스핀락 방식은 기다리는 동안 계속 CPU를 낭비한다는 단점이 있다.

그래서 락이 준비되면 나를 깨워 ㅋㅋ 라는 방식의 락이 등장한다.

----------------------------------------------------------------------------------------------------------------
뮤텍스(mutex)

class Mutex{
    int value = 1;
    int guard = 0;
}

Mutex::lock(){
    while(test_and_set(&guard));
    if(value == 0{
        ... 현재 스레드를 큐에 넣음;
        guard = 0; & go to sleep
    }
    else{
        value = 0;
        guard = 0;
    }
}

Mutex::unlock(){
    while(test_and_set(&guard));
    if(큐에 하나라도 대기중이라면){
        그 중에 하나를 깨운다;
    }
    else{
        value = 1;
    }
    guard = 0;
}

mutex -> lock();
...critical section
mutex -> unlock();

value 설명

초기 value는 1로 시작한다.
스레드는 lock을 얻기 위해서 lock()을 동작 시키는데
만약 value가 1이라면 바로 critical section이 진행되고 value = 0 으로 바꿔준다.
만약 value가 0이라면 현재 critical section을 사용하고 있는 프로세스/스레드가 있으니까
현재 스레드들 큐에 넣어주고 스레드를 sleep 시킨다.

unlock()
만약 큐에 스레드가 하나라도 대기중이라면 그중에 하나를 깨우고 critical section을 진행시키게 한다. 물론 들어온 순서 아닐까
없다면 다시 value로 1을 만들어서 제일먼저 다시 critical section을 사용하고 싶은 스레드에게 락을 얻을 수 있게 한다.

guard 설명

value 또한 여러 프로세스/스레드가 사용하는 전역 변수이다.(공유 데이터)
그럼 value 또한 race condition(경쟁 조건)이 발생 가능하다는 뜻이다.
그걸 막아줄 장치가 필요한데 그게 바로 guard 이다.

while(test_and_set(&guard)); 을 통해서 guard를 얻게 하고
guard를 얻고 lock()이든 unlock()이든 다시 guard = 0; 으로 풀어준다.

핵심 사항 2가지
if(value == 0){
    .. 현재 스레드를 큐에 넣음; --> 이 부분으로 cpu 사이클을 최소화 시킨다.
}

mutex 또한 while(test_and_set(&guard)); -> cpu level의 atomic 명령어를 사용한다.

정리
mutex(뮤텍스) -> 락을 가질 수 있을 대 까지 휴식하는 방식

그렇다면 뮤텍스가 스핀락보다 항상 좋은걸까?
멀티 코어 환경이고, critical section에서의 작업이 컨텍스트 스위칭보다 더 빨리 끝난다면 스핀락이 뮤텍스보다 더 이점이 있다.
뮤텍스 같은 경우에는 락을 얻을 수 없는 상황에서 컨텍스트 스위칭이 발생한다. -> sleep ans wake

-----------------------------------------------------------------------------------------------------------
semaphore(세마포)

signal mechanism을 가지는 하나 이상의 프로세스/스레드가 critical section에 접근 가능하도록 하는 장치

class Semaphore{
     int value = 1;  // semaphore에서는 value 초기값이 0,1,2...
     int guard = 0;
}

Semaphore::wait(){
    while(test_and_set(&guard));
    if(value == 0){
        ... 현재 스레드를 큐에 넣음
        guard = 0; & go to sleep
    }
    else{
        value -= 1; // mutex: value = 0
        guard = 0;
    }
}

Semaphore::signal(){
    while(test_and_set(&guard));
    if(큐에 하나라도 대기중이라면){
        그 중에 하나를 꺠워서 준비시킨다.
    }
    else{
        value += 1; // mutex : value = 1
    }
    guard = 0;
}

semaphore->wait();
... critical section
semaphore->signal();

semaphore 비유 공중 화장실에 변기가 3개라면 3명까지는 변기를 사용이 가능하다.
믈론 semaphore 또한 mutual exclusion을 보장 가능하게 만들 수 있다. -> value = 1
mutual exclusion(상호 배제) -> 한개의 프로세스/스레드만 critical section에서 실행 가능하다

value = 1을 가지는 세마포를 이진 세마포 또는 바이너리 세마포라고 한다.
value !=1 을 가지는 세마포를 counting semaphore(카운팅 세마포)라고 한다.

아까 설명에 세마포는 signal mechanism을 가진다고 했었다.

signal mechanism?

세마포는 순서를 정해줄 때 사용

프로세스 2개가 있다. P1, P2 우리는 항상 P1 에서 동작하는 task1이 끝나고 P2에서 동작하는 task3가 동작하게 만들고 싶다.
semaphore는 이렇게 순서를 정해줄 수 있다.

class Semaphore{
     int value = 0;
     int guard = 0;
}

P1                               P2
task1                            task2
sempahore -> signal()            sempahore -> wait()
                                 task3


case1 : task1이 먼저 동작하고 signal()
task1이 종료하고 signal() value += 1  -> value = 1
wait()에서 value = 1이기 때문에 바로 value -= 1 -> value = 0
task3 동작

case2 : task2가 먼저 동작하고 wait()
semaphore -> wait() value = 0이기 때문에 task3는 진행 하지 않고 큐에 넣는다.

P1에서 task1이 끝나고 semaphore -> singal()을 하여 value = 1로 바꾸면
task3는 큐에서 나와 진행

case1, case2 모두 결국 task1 -> task3 형태로 동작하게 된다. 즉 순서를 정해준다.
이런 매커니즘을 signal mechanism 이라고 한다.

------------------------------------------------------------------------------------
뮤텍스와 이진(binary) 세마포는 같은 것 아닌가?
둘다 value = 1

- 뮤텍스는 락을 가진 자만 락을 해제 할 수 있지만 세마포는 그렇지 않다.
- 뮤텍스는 priority inheritance 속성을 가진다. 세마포는 그 속성이 없다.

P1의 우선순위는 high P2의 우선순위는 Low 가정 CPU 스케줄링 관점

만약 우선순위가 낮은 P2가 락을 가지고 있는데 CPU 우선순위가 낮으면 P1은 계속 기다려야 한다.
그래서 이때 P2의 우선순위를 HIGH로 올려서 CPU가 빨리 처리하게 만든다 -> priority inheritance 속성
세마포에서는 누가 signal을 날릴지 알 수 없기 때문에 이런 속성이 없다.

상호 배제만 필요하다면 뮤텍스 작업 간의 실행 순서 동기화가 필요하다면 세마포를 권장한다.

스핀락, 뮤텍스, 세마포의 구체적인 동작 방식은 OS와 프로그래밍 언어에 따라 조금씩 다르다 그러니 관련 문서를 잘 참고하자

 */
