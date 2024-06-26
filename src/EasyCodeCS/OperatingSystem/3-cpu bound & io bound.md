# cpu bound & io bound

### 용어 정리 

- CPU
  - central processing unit
  - 프로세스의 명렁어를 해석하고 실행하는 장치
- IO
  - input/output
  - 파일을 읽고 쓰기 
  - 네트워크의 어딘가와 데이터를 주고 받기 
  - 입출력 장치와 데이터를 주거나 받는 것
- 버스트
  - Burst
  - 어떤 현상이 짧은 시간 안에 집중적으로 일어나는 일
- CPU 버스트 
  - 프로세스가 cpu에서 한번에 연속적으로 실행되는 시간 
- IO 버스트
  - 프로세스가 IO 작업을 요청하고 결과를 기다리는 시간 
- CPU bound 프로세스
  - CPU burst가 많은 프로세스 
  - ex) 동영상 편집 프로그램, 머신러닝 프로그램(코어가 수천개인 GPU를 사용)
- IO bound 프로세스
  - IO burst가 많은 프로세스 
  - 일반적인 벡엔드 API 서버 

### CPU bound 프로그램과 스레드 개수 

Question
```text
듀얼 코어 cpu에서 동작할 cpu bound 프로그램을 구현한다면 
몇 개의 스레드를 쓰는게 좋을까?
```

Goetz (2002, 2006) recommends
- CPU bound 프로그램에서 적절한 스레드 수는 number of cpus + 1

Opinion
```text
1. 듀얼 코어 cpu에서 스레드 4개를 사용한다고 가정 
오늘날은 멀티 태스킹 환경에서 프로그램이 돌아간다. 
그럼 1개의 cpu(core)당 2개의 스레드가 컨텍스트 스위칭을 하면서 동작을 하게 된다.  
그런데 프로그램 특성상 cpu bound 프로그램은 io 작업이 (상대적으로)적으니까
굳이 컨텍스트 스위칭을 할 필요가 없다.
컨텍스트 스위칭은 cpu 입장에서 오버헤드이다. 

2. 듀얼 코어 cpu에서 스레드 2개를 사용한다고 가정 
1개의 cpu(core)당 1개의 스레드가 1:1 매칭 되어서 동작을 하게 된다.
그럼 컨텍스트 스위칭이라는 무거운 작업 없이 쭉 작업을 진행할 수  있게된다. 
  
그래서 Goetz라는 사람은 cpu bound 프로그램에서 적절한 스레드 수를 cpu 코어 개수와 같거나
+ 1개의 스레드 개수를 추천한다. 
```

### IO bound 프로그램과 스레드 개수 

- IO bound 프로그램은 스레드 몇 개로 구현이 적절할까? 
  - 여러 상황에 맞춰서 적절한 스레드 수를 찾아야 한다
  - 컴퓨터 스펙, 애플리케이션 특징에 맞게 
- API 서버가 thread per request 방식이라면?
  - 몇 개의 스레드들을 미리 만들어 놓을지 여러 상황을 고려해서 결정하는 것이 필요