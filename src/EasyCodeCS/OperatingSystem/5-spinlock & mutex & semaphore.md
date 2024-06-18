# spinlock & mutex & semaphore

### 복습
- race condition(경쟁 조건)
  - 여러 프로세스/스레드가 동시에 같은 데이터를 조작할 때 타이밍이나 접근 순서에 따라
    결과가 달라질 수 있는 상황
- synchronization(동기화)
  - 여러 프로세스/스레드를 동시에 실행해도 공유 데이터의 일관성을 유지하는 것 
- critical section(임계 영역)
  - 공유 데이터의 일관성을 보장하기 위해 하나의 프로세스/스레드만 진입해서 실행 가능한 영역 
  - mutual exclusion(상호 배제): 하나의 프로세스/스레드만 critical section 진입을 보장

어떻게 mutual exclusion 보장을 할까? -> 락 

### 스핀락(spinlock)

```c
volatile int lock = 0; // global

void critical(){
    while(test_and_set(&lock) == 1);
        ... critical section
    lock = 0;    
}

int test_and_set(int* lockPtr){
    int old Lock = *lockPtr;
    *lockPtr = 1;
    return oldLock;
}
```

- ``lock``: 전역변수 0은 락이 해제된 상태 1은 락이 설정된 상태 
  - ``volatile`` 항상 메모리에서 읽고 쓰도록 함 여러 스레드가 접근하는 변수 값을 일관성있게 유지 
- ``critical()``
  - ``while(test_and_set(&lock) == 1);``
    - test_and_set 함수를 호출해서 락을 획득하려고 시도
    - 락이 이미 설정되어 있다면 test_and_set 1을 반환하고 while 루프 반복
    - 락이 해제되면 test_and_set 0 반환 루프를 탈출 
  - critical section(임계 영역)
  - ``lock = 0;`` 임계 영역을 빠져나오면 lock  0으로 설정 락을 해제 
- ``test_and_set(int* lockPtr)``
  - 이 함수는 원자적으로 lockPtr이 가리키는 값을 1로 설정 이전 값을 반환 
  - ``int old Lock= *lockPtr;``
    - 현재 lock 값을 old Lock에 저장 
  - ``*lockPtr = 1;`` 
    - lock 값을 1로 설정 
  - ``return oldLock;``
    - 이전 값을 반환 

시나리오로 이해하기 
```text
T1 스레드, T2 스레드 존재 

T1 스레드 시작 
while(test_and_set(&lock) == 1); 0 반환 

while문 탈출 

critical section 진입 

T2 스레드 시작 
while(test_and_set(&lock) == 1); 1 반환

while문 반복 

T1 스레드 
critical section 끝 

lock = 0 락 해제 

T2 스레드 
test_and_set 계속 확인 하다가 T1 스레드가 락 해제를 한 시점에 
while문 탈출 

critical section 진입
critical section 끝 

lock = 0 락 해제 
```

여기서 드는 의문점 
```text
만약 lock = 0 

T1 스레드, T2 스레드가 동시에 while문에 진입해서 test_and_set을 호출 한다면?

두 스레드 모두 critical section 진입 하는게 아닌가? 

test_and_set에는 숨겨진 비밀이 있다. 
```

TestAndSet 
- CPU atomic 명령어
- 실행 중간에 간섭받거나 중단되지 않는다
- 같은 메모리 영역에 대해 동시에 실행되지 않는다 

스핀락(spinlock)
- 락을 가질 수 있을 때 까지 반복해서 시도
- 기다리는 동안 계속 CPU를 낭비한다.
  - 락이 있는지 없는지 확인하는 작업

### 뮤텍스(mutex)

```c
class Mutex{
  int value = 1;
  int guard = 0;
}

Mutex::lock(){
  while(test_and_set(&guard));
  if(value == 0){
      .. 현재 스레드를 큐에 넣음;
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
    value= 1;
  }
  guard = 0;  
}

mutex -> lock();
.. critical section
mutex -> unlock();
```

뮤텍스(Mutex) - 락을 가질 수 있을 때 까지 휴식 

뮤텍스가 스핀락보다 항상 좋은걸까?
```text
멀티 코어 환경이고 
critical section에서의 작업이 
컨텍스트 스위칭보다 더 빨리 끝난다면 
스핀락이 뮤텍스보다 더 이점이 있다. 
```

### 세마포(semaphore)

- signal mechanism을 가진, 하나 이상의 프로세스/스레드가 critical section에 접근 
  가능하도록 하는 장치 

```c
class Semaphore{
  int value = 1;
  int guard = 0;
}

Semaphore::wait(){
  while(test_and_set(&guard));
  if(value == 0){
      .. 현재 스레드를 큐에 넣음;
      guard = 0; & go to sleep
  }
  else{
      value -= 1;
      guard = 0;
  }
}

Semaphore::signal(){
  while(test_and_set(&guard));
  if(큐에 하나라도 대기중이라면){
     그 중에 하나를 깨워서 준비 시킨다
  }
  else{
    value += 1;
  }
  guard = 0;  
}

Semaphore -> wait();
.. critical section
Semaphore-> signal();
```

Semaphore value가 1이상인 이유 
```text
공중화장실 변기 같은 경우는 1개이상이다. 
이럴 때 변기 개수 만큼 허용하도록 value 값을 변기 개수에 맞춘다. 

즉 여러 개의 자원에 대한 접근을 제어하기 위해서이다.
```
- value = 1
  - binary semaphore
- value > 1
  - counting semaphore

세마포는 순서을 정해줄 때 사용 
```text

P1 프로세스 
   task1 
   semaphore -> signal()
   
P2 프로세스
   task2
   semaphore -> wait()
   task3

class Semaphore{
    int value = 0;
    int guard = 0;
}   
   
   
P2 프로세스의 task3는 P1 프로세스의 task1 이후의 실행을 보장이 가능하다.


case1)

P1 프로세스 
task1 -> semaphore -> signal() -> value 1증가 

P2 프로세스
task2 -> semaphore -> wait() -> value 1이기 때문에 바로 task3 진행 

case2)

P2 프로세스 
task2 -> semaphore -> wait() -> value 0이기 때문에 큐에가서 sleep

P3 프로세스
task1 -> semaphore -> siganl() -> 큐에 자고 있던 P2 프로세스를 깨운다. 

P2 프로세스
taks3

----------------------------------------------------------------------
정리 
case1), case2) 모두 task3는 task1이 끝난 이후 진행 가능하다.
즉 세마포는 이렇게 순서를 정해줄 때 사용이 가능하다.      
```

뮤텍스와 이진(binary)는 같은 것 아닌가?
```text
뮤텍스는 락을 가진 자만 락을 해제 할 수 있다 
하지만 세마포는 그렇지 않다. 

뮤텍스는 priority inheritance 속성을 가진다. 
세마포는 그 속성이 없다. 

priority inheritance?

P1(우선순위 high)
P2(우선순위 low)

P2 프로세스 먼저 진행 case

P2 프로세스 락 획득 

이때 P2 프로세스가 락 획득을 한 상태에서 다른 프로세스 (P3) 컨텍스트 스위칭 발생

그럼 P1 프로세스는 우선순위가 높지만 P2 프로세스가 락을 가지고 있어서 

P2 프로세스가 락을 반환하기 전까지 더이상 작업을 진행하지 못한다. 

이때 CPU는 P2 프로세스의 우선순위를 P1만큼 올려준다. (priority inheritance)

이게 가능한 이유는 누가 뮤텍스는 누가 락을 취득했는지 반납하는지를 알아서 이다.

세마포는 이런 속성이 없다. 
```

정리
```text
상호 배제만 필요하다면 뮤텍스를,
작업 간의 실행 순서 동기화가 필요하다면 
세마포를 권장한다. 
```


