# CPU 스케줄러 프로세스 스케줄링

### 목표 

- CPU scheduler & dispatcher 차이 이해 
- 스케줄링 선점 방식 
- 다양한 스케줄링 알고리즘 

### CPU scheduler vs dispatcher 

스케줄러(scheduler)역할 
![17.png](Image%2F17.png)
- cpu에서 실행될 프로세스를 선택하는 역할 
- ready queue: ready state 프로세스들이 기다리는 큐  
- 스케줄러는 어떤 프로세스가 ready queue 에서 실행할 것인지를 선택

디스패처(dispatcher)역할 
- 스케줄러가 선택한 프로세스가 cpu에서 실행될 수 있도록 만드는 작업을 한다.
- 컨텍스트 스위칭(context switching)
- 컨텍스트 스위칭이 끝난 이후 커널 모드 ➡️ 유저 모드 변경 
  - 컨텍스트 스위칭은 민감한 작업(커널 모드)에서 한다. 
- 선택된 프로세스가 실행해야 하는 적절한 위치로 이동 
- 선택된 프로세스에게 CPU를 할당 

### 스케줄링 선점 방식 

- Non preemptive scheduling (비선점 스케줄링)
  - running state 프로세스가 자발적으로 동작 OS 직접적인 개입 x
  - running ➡️ terminated
  - running ➡️ waiting
  - running ➡️ ready
  - 신사적, 협력적(cooperative), 느린 응답성 
- Preemptive scheduling (선점 스케줄링)
  - Non preemptive scheduling + OS 개입 
  - running ➡️ ready (cpu time(time slice) 소비를 다 함) 강제적 
  - ready ➡️ running 어떤 프로세스가 waiting 작업에서 ready 상태로 바뀌었을 때 바로 실행 
    기존 running state 프로세스는 강제적으로 ready 상태로 바뀐다. 
  - 적극적, 강제적, 빠른 응답성, 데이터 일관성 문제 

### 스케줄링 알고리즘 

- FCFS(first-come, first-served)
  - 먼저 도착한 순서대로 처리 
  - Queue(FIFO)
- SJF(shortest-job-first)
  - 프로세스의 다음 cpu burst가 가장 짧은 프로세스부터 실행 
- SRTF(shortest-remaining-time-first)   
  - 남은 cpu time가 가장 짧은 프로세스부터 실행 
- Priority
  - 우선순위가 높은 프로세스부터 실행 
- RR(round-robin)
  - time slice로 나눠진 cpu time을 번갈아가며 실행 
- Multilevel Queue
  - 프로세스들을 그룹화해서 그룹마다 큐를 두는 방식 
