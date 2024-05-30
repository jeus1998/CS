
# 프로토콜 표준 스펙에서 정의한 Socket(소켓), Port(포트), TCP connection(연결) 개념

![12.JPG](Image%2F12.JPG)

### TCP/IP stack 구분
- application layer
  - 애플리케이션 레벨에서 구현/관리
  - 네트워크 기능을 사용하는데 목적 
  - APPLICATION
- transport layer + internet layer + link layer 
  - 하드웨어/펌웨어, OS 레벨에서 구현/관리
  - 네트워크 기능을 지원하는데 목적 
  - SYSTEM

### Port
- process와 연결된 data path 혹은 data channel
- system 🔄 application(process) 연결 통로 
- port 식별 👉 port name을 통해 식별
  - 각 os마다 다르다.

### 프로세스 간의 통신과 인터넷 프로토콜

![13.JPG](Image%2F13.JPG)

- IP 
  - internet protocol
  - 데이터를 목적하는 host까지 라우팅 하는 역할 
  - 최적의 경로 
  - unreliable: 데이터 손실(data loss), 데이터 순서 보장❌(out-of-order) 
- TCP
  - transmission control protocol
  - 프로세스 간의 통신에서는 데이터를 안정적(reliable)으로 주고 받을 수 있는 프로토콜이 필요
- TCP - Connection
  - 프로세스 간의 안정적이고 논리적인 통신 경로 
  - connection open(3-way-handshake) ➡️ 데이터 전송 or 수신 ➡️ connection close(4-way-handshake)
  - connection-oriented 
- Port number
  - 인터넷 상에서 어떻게 port를 유니크하게 식별할까? ➡️ port number 정의
  - 16 bits로 이루어진 숫자(0 ~ 65535)
- Socket
  - port number만으로는 유니크하게 식별할 수 없다. 
  - internet address(IP)로 각 host를 유니크하게 식별할 수 있다.
  - internet address(IP) + port number ➡️ socket
  - 인터넷 상에 존재하는 각 port를 유니크하게 식별하기 위한 주소 
  - 각 socket은 인터넷 상에서 유니크하다.


### TCP 관점 Connection & Socket 관계

![14.JPG](Image%2F14.JPG)

- 각 connection을 유니크하게 식별할 수 있어야 한다. 
- 한 쌍의 socket은 connection을 유니크하게 식별한다. 
- <client socket(ip + port), server socket(ip + port)> ➡️ connection
- 하나의 socket은 동시에 여러 connection들에서 사용될 수 있다.

### UDP 관점 Connection & Socket 관계

- user datagram protocol
- connectionless: 연결을 맺지 않고 바로 데이터를 주고 받는다. 
- unreliable: internet protocol을 거의 그대로 사용 
- UDP 표준(RFC 768)을 보면 socket 단어가 등장하지 않는다.
- 이후에 자연스럽게 UDP에서도 socket 개념을 쓰기 시작
- TCP/IP stack에서 socket은 <protocol, ip address, port number>로 유니크하게 식별 

### 표준에서 정의한 개념을 바탕으로 예제 

- <TCP, 50.50.50.50, 8081> socket <TCP, 50.50.50.50, 8081> socket
   - 이렇게 socket 2개 존재가 가능할까? ➡️ ❌
   - protocol or IP or port 3개 중에 하나라도 달라야 한다. 
- <UDP, 50.50.50.50, 8081> socket <TCP, 50.50.50.50, 8081> socket
   - 이렇게 socket 2개 존재가 가능할까? ➡️ ⭕️
- <TCP, 90.90.90.90, 8081> socket <TCP, 50.50.50.50, 8081> socket
   - 이렇게 socket 2개 존재가 가능할까? ➡️ ⭕️ 
   - 하나의 컴퓨터에서 여러가지 IP 주소를 갖는게 가능하다. 

![15.JPG](Image%2F15.JPG)

![16.JPG](Image%2F16.JPG)

### 주의 사항

- 실제로 네트워크 프로그래밍 할 때는 소켓의 개념과 의미가 미묘하게 다르다
- 특히 소켓을 식별하는 방법에서 큰 차이가 있다.
