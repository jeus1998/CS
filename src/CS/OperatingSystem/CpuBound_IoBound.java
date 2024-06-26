package CS.OperatingSystem;

/**
 * [쉬운 코드] : https://www.youtube.com/watch?v=qnVKEwjG_gM&list=PLcXyemr8ZeoT-_8yBc_p_lVwRRqUaN8ET&index=8
 * 주제: cpu bound, io bound
 */
public class CpuBound_IoBound {}
/*
목표
1. CPU bound, IO bound 의미 이해
2. 이 두가지가 프로그램의 스레드 개수 결정에 어떤 영향을 주는지 이해

CPU(central processing unit) - 프로세스의 명령어를 해석하고 실행하는 장치
IO(input/output) - 파일을 읽고 쓰거나 네트워크의 어딘가와 데이터를 주고 받는 것 IO 디바이스와 데이터를 주고 받는 것
버스트(Burst) - 어떤 현상이 짧은 시간 안에 집중적으로 일어나는 일

CPU Burst - 프로세스가 CPU에서 한번에 연속적으로 실행되는 시간
IO Burst - 프로세스가 IO 작업을 요청하고 결과를 기다리는 시간

프로세스의 인생은 CPU 버스트와 IO 버스트의 연속

CPU bound 프로세스
- CPU burst가 많은 프로세스 / IO burst가 적은 프로세스
- 동영상 편집 프로그램, 머신러닝 프로그램
- 입출력은 적고 연산이 많은 프로세스

IO bound 프로세스
- IO burst가 많은 프로세스 / CPU burst가 적은 프로세스
- (일반적인) 백엔드 API 서버
- 입출력은 많고 연산이 적은 프로세스

--------------------------------------------------------------------------------------------
Q1 : 듀얼 코어 CPU에서 동작할 CPU bound 프로그램을 구현한다면 몇개의 스레드를 쓰는게 좋을까?

A1:
Goetz (2002, 2006) recomments
CPU bound 프로그램에서 적절한 스레드 수는 cpu 개수 or cpu 개수 + 1

스레드 개수가 많으면 그만큼 컨텍스트 스위칭이 더 자주 일어나니까 cpu 입장에서는 오버헤드가 더 많이 일어난다.
컨텍스트 스위칭 = 교체에 필요한 cpu 추가 비용 유발

만약 스레드 개수 = 코어 개수

1:1 매칭 컨텍스트 스위칭이 발생하지 않는다!

그래서 스레드 개수 = 코어 개수 or 코어 개수 + 1

--------------------------------------------------------------------------------------------
그렇다면 IO bound 프로그램은 스레드 몇 개로 구현이 적당할까?

여러 상황에 맞춰서 적절한 스레드 수를 찾아야 한다 (정확한 가이드 라인이 없다)

만약 API 서버가 thread per request 방식이라면? (api 요청 마다 전담 스레드를 할당하는 방식)

몇 개의 스레드들을 미리 만들어 놓을지(스레드풀) 여러 상황을 고려해서 결정하는 것이 필요

 */