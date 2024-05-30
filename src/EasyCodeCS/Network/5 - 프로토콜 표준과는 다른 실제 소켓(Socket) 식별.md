
# 프로토콜 표준과는 다른 실제 소켓(Socket) 식별

- TCP/IP stack : 4layer 존재 
   - application layer 
   - transport layer
   - internet layer
   - link layer
- TCP/IP stack : 4layer 계층 분리 
   - application: application layer 
     - 애플리케이션 레벨에서 구현/관리
     - 네트워크 기능을 사용하는데 목적
   - system: transport layer, internet layer, link layer
     - 하드웨어/펌웨어, OS 레벨에서 구현/관리
     - 네트워크 기능을 지원하는데 목적
- Socket
   - 애플리케이션이 시스템의 기능을 함부로 쓸 순 없다.
   - 시스템은 애플리케션이 네트워크 기능을 사용할 수 있도록 프로그래밍 인터페이스를 제공한다. 
   - 프로그래밍 인터페이스 👉 socket
   - 애플리케이션은 socket을 통해 데이터를 주고 받는다.
   - 개발자는 socket programming을 통해 네트워크 상의 다른 프로세스와 
     데이터를 주고 받을 수 있도록 구현한다.

### c언어 socket programming 예시 

![17.JPG](Image%2F17.JPG)

### Socket

- 대부분의 시스템은 socket 형태로 네트워크 기능 제공 (os 관점)
- 지금까지 나는 HTTP로 통신을 했는데 socket은 뭐지?
  - 보통 개발자가 직접 socket을 조작해서 통신 기능을 구현할 일은 적다
  - application layer의 프로토콜은 보통 라이브러리나 모듈 형태로 해당 기능이 제공 
  - 해당 내부를 보면 소켓을 활용해서 프로토콜을 구현했음을 알 수 있다.

### 실제 구현/동작 관점에서 소켓 정의

![18.JPG](Image%2F18.JPG)

- 프로토콜 표준에서 정의한 것 처럼 socket은 <protocol, IP, port>로 유니크하게 식별되는가? 
  - UDP ⭕️
  - TCP ❌
  - 프로토콜 스펙을 시스템 레벨에서 구현하면서 조금 달라짐!

실제 TCP socket 동작 방식 
- https://www.youtube.com/watch?v=WwseO8l8rZc&list=PLcXyemr8ZeoSGlzhlw4gmpNGicIL4kMcX&index=5
- 8:36


### Port number 

- 16 bits로 이루어진 숫자(0 ~ 65535)
  - 0 ~ 1023: well-known ports, system ports
    - http(80)
    - https(443)
    - dns(53)
  - 1024 ~ 49151: registered ports (IANA)에 등록된 번호 
    - MySQL DB(3306)
    - Apache tomcat server(8080)
  - 49152 ~ 65535: dynamic ports(등록 안된 번호, 임시로 혹은 자동 할당될 때 사용)

### 💯 정리

![19.JPG](Image%2F19.JPG)
