# Introduction to Operating Systems

## 강의 목표

- 운영체제 개요
- 컴퓨터시스템의 구조
- 프로세스 관리
- CPU 스케줄링
- 병행 제어
- 데드락
- 메모리 관리
- 가상 메모리
- 파일 시스템 
- 입출력 시스템
- 디스크 관리

## 운영체제란 무엇인가?

- 컴퓨터 하드웨어 바로 위에 설치되어 사용자 및 다른 모든 소프트웨어와 하드웨어를 연결하는 소프트웨어 계층
- 운영체제(커널)
  - 운영체제 핵심 부분 메모리에 상주하는 부분 

## 운영체제의 분류 방법

### 동시 작업 가능 여부
- 단일 작업(Single tasking)
  - 한 번에 하나의 작업 처리 
  - ex) MS-DOS 프롬프트 상에서는 한 명령의 수행을 끝내기 전에 다른 명령을 수행시킬 수 없음 
- 다중 작업(Multi tasking)
  - 동시에 두 개 이상의 작업 처리 
  - ex) UNIX, MS Windows 등에서는 한 명령의 수행이 끝나기 전에 다른 명령이나 프로그램 수행 가능

### 사용자 수
- 단일 사용자(single user)
  - MS-DOS, MS Windows

- 다중 사용자(multi user)
  - UNIX, NT server

### 처리 방식 

- 일괄 처리(batch programming)
  - 작업 요청의 일정량 모아서 한꺼번에 처리 
  - 작업이 완전 종료될 때까지 기다려야 함 
  - 초기 Punch Card 처리 시스템 

![1.JPG](Image%2F1.JPG)

- 시분할 방식(time sharing)
  - 여러 작업을 수행할 때 컴퓨터 처리 능력을 일정한 시간 단위로 분할하여 사용 
  - 일괄 처리 시스템에 비해 짧은 응답 시간을 가짐 
  - 예) UNIX 
  - interactive한 방식 
  - 데드라인이 존재하지 않다.

- 실시간(Realtime OS)  
  - 정해진 시간 안에 어떠한 일이 반드시 종료됨을 보장되어야 하는 실시간 시스템을 위한 OS
  - 미사일 제어, 반도체 장비, 로보트 제어, 원자로/공장 제어

### 컴퓨터에서 여러 작업을 동시에 수행한다는 것을 뜻하는 몇 가지 용어 

- MultiTasking 
- MultiProgramming - 메모리 관점
  - 여러 프로그램이 메모리에 올라가 있음을 강조 
- Time Sharing - CPU 관점 
  - CPU의 시간을 분할하여 나누어 쓴다는 의미를 강조 
- MultiProcess
- MultiProcessor
  - 하나의 컴퓨터에 CPU(processor)가 여러 개 붙어 있음을 의미 

### 유닉스(UNIX) 

- 코드의 대부분 C언어
- 높은 이식성
- 최소한의 커널 구조
- 복잡한 시스템에 맞게 확장 용이
- 소스 코드 공개
- 프로그램 개발에 용이
- 다양한 버전
  - System V, FreeBSD, SunOs
  - Linux





