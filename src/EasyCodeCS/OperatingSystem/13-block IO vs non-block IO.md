# block I/O VS non-block I/O

### I/O

```text
input/output, 데이터의 입출력 

I/O 종류
- network(socket)
- file
- pipe
- device 
```

### socket 

네트워크 통신은 socket을 통해 데이터가 입출력 된다
![20.png](Image%2F20.png)

backend server
- 네트워크 상의 요청자들과 각각 소켓을 열고 통신한다 

### OS레벨에서 block I/O 동작 원리

I/O 작업을 요청한 프로세스/스레드는 요청이 완료될 때까지 블락됨 
![21.png](Image%2F21.png)

### OS레벨에서 non block I/O 동작 원리

- 프로세스/스레드를 블락시키지 않고 요청에 대한 현재 상태를 즉시 리턴 
- 블락되지 않고 즉시 리턴하기 때문에 스레드가 다른 작업을 수행할 수 있다

![22.png](Image%2F22.png)

### 동기/비동기 & 블로킹/논블로킹 

- 동기/비동기: 작업을 순차적으로 수행할지 아닌지에 대한 관점
- 블로킹/논블록킹: 현재 작업이 block(차단, 대기) 다른 작업을 수행할 수 있는지에 대한 관점

### 동기(Synchronous) / 비동기(Asynchronous)

- Synchronous: 요청한 작업에 대해 완료 여부를 따져 순서대로 처리 
- Asynchronous: 요청한 작업에 대해 완료 여부를 따지지 않는다 자신의 다음 작업을 그대로 수행 

![23.png](Image%2F23.png)

### 비동기의 성능 이점

```text
보통 비동기 특징을 이용하여 성능과 연관지어 말한다.
왜냐하면 요청한 작업에 대하여 완료 여부를 신경쓰지 않고 자신의 그다음 작업을 수행한다는 것은, 
I/O 작업과 같은 느린 작업이 발생할 때, 기다리지 않고 다른 작업을 처리하면서 동시에 처리하여 멀티 작업을 진행할수 있기 때문이다. 
이는 전반적인 시스템 성능 향상에 도움을 줄 수 있다.
```

![24.png](Image%2F24.png)

### 동기(Synchronous) / 비동기(Asynchronous) & Blocking / Non Blocking

![25.png](Image%2F25.png)


### 참고자료
- [Inpa Dev](https://inpa.tistory.com/entry/%F0%9F%91%A9%E2%80%8D%F0%9F%92%BB-%EB%8F%99%EA%B8%B0%EB%B9%84%EB%8F%99%EA%B8%B0-%EB%B8%94%EB%A1%9C%ED%82%B9%EB%85%BC%EB%B8%94%EB%A1%9C%ED%82%B9-%EA%B0%9C%EB%85%90-%EC%A0%95%EB%A6%AC)
- [쉬운 코드](https://www.youtube.com/watch?v=mb-QHxVfmcs&list=PLcXyemr8ZeoQOtSUjwaer0VMJSMfa-9G-&index=13)


