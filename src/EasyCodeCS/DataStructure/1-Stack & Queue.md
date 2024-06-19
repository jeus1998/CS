# 스택 & 큐 

### Abstract Data Type vs Data Structure

- ADT(Abstract Data Type)
  - 추상 자료형 
  - 개념적으로 어떤 동작이 있는지만 정의 
  - 구현에 대해서는 다루지 않음 
- DS(Data Structure)
  - 자료구조
  - ADT에서 정의된 동작을 실제로 구현 

### 스택

- 스택(STACK)
- LIFO(Last In First Out) 형태로 데이터를 저장하는 구조 
- 스택 주요 동작 
  - push
  - pop
  - peek
- 사용 사례
  - stack memory & stack frame

### 큐

- 큐(Queue)
- FIFO(First In First Out) 형태로 데이터를 저장하는 구조 
- 큐 주요 동작
  - add
  - poll
  - peek
- 사용 사례
  - producer/consumer architecture
  - monitor
    - entry queue(mutex 락)
    - waiting queue(condition variable)

### 기술 문서에서 큐를 만난다면?

- 항상 FIFO을 의미하지는 않음 
- ex) cpu 프로세스/스레드 스케줄링 ready queue 

### 스택/큐 관련 에러와 해결 방법 

- StackOverFlowError 
  - 스택 메모리 공간을 다 썼을 때 발생하는 에러 
  - 재귀함수(recursive function)에서 탈출 못해서 발생 
- OutOfMemoryError
  - Java의 힙(heap) 메모리를 다 썼을 때 발생 
  - 큐에 데이터가 계속 쌓이기만 한다면 발생 
  - 해결방법 
    - 큐 사이즈를 고정 
    - 예외 던지기
    - 특별한 값(null of false) 반환
    - 성공할 때 까지 영원히 스레드 블락(block)
    - 제한된 시간만 블락하고 그래도 안되면 포기 
  - LinkedBlockingQueue 





