
# 프로토콜 & OSI 7 layer

### 네트워크 기능

- 애플리케이션 목적에 맞는 통신 방법 제공
- 신뢰할 수 있는 데이터 전송 방법 제공
- 네트워크 간의 최적의 통신 경로 결정
- 목적지로 데이터 전송 
- 노스 사이의 데이터 전송

### 프로토콜의 필요성

- 사람 🔄 사람 - 서로 대화를 하려고 하면 같은 언어를 사용
- 통신 🔄 통신 - 통신 기능이 제대로 동작하기 위해서는 약속된 통신 방법이 있어야함 ➡️ 프로토콜

### 네트워크 프로토콜(network protocol)

- 네트워크 통신을 하기 위해서 통신에 참여하는 주체들이 따라야 하는 형식, 절차, 규약

### 네트워크 프로토콜 모듈화 

- 네트워크의 모든 기능은 단 하나의 프로토콜로 구현할 수 없다! ➡️ 모듈화 
- 각 기능이 계층별로 동작한다. ➡️ layered architecture(계층 구조) 

### 네트워크 계층 구조 모델 

- OSI model (7 layer)
  - 범용적인 네트워크 구조
- TCP/IP stack (4 layer)
  - 인터넷에 특화된 네트워크 구조 

### OSI 7 layer

![10.JPG](Image%2F10.JPG)

- 각 레이어에 맞게 프로토콜이 세분화돼서 구현 
- 각 레이어의 프로토콜은 하위 레어어의 프로토콜이 제공하는 기능을 사용하여 동작 
- ex) L3 레이어 프로토콜은 L2 레이어 프로토콜이 제공하는 기능을 사용 

--------------------------------------------------------------------

- application layer 
  - 애플리케이션 목적에 맞는 통신 방법 제공 
  - HTTP, DNS, SMTP, FTP ...
  - 데이터를 어떻게 전송하는지는 application layer의 관심사가 아니다. 해당 기술은 하위 레이어의 기능을 사용 
- presentation layer
  - 애플리케이션 간의 통신에서 메시지 포멧 관리
  - 인코딩 🔄 디코딩
  - 암호화 🔄 복호화
  - 압축 🔄 압축 풀기
- session layer 
  - 애플리케이션 간의 통신에서 세션 관리
  - RPC(remote procedure call)
- transport layer
  - 애플리케이션 간의 통신 담당
  - 목적지 애플리케이션으로 데이터 전송 
  - 안정적이고 신뢰할 수 있는 데이터 전송 보장(TCP)
  - 필수 기능만 제공(UDP)
- network layer 
  - 호스트 간의 통신 담당 (IP)
  - 목적지 호스트로 데이터 전송 
  - 네트워크 간의 최적의 경로 결정 
- data link layer
  - 직접 연결된 노드 간의 통신 담당 
  - MAC 주소 기반 통신 (ARP)
- physical layer
  - bits 단위로 데이터 전송 

### OSI 7 layer 모델에서 구체적인 통신 예제

![11.JPG](Image%2F11.JPG)

- https://www.youtube.com/watch?v=6l7xP7AnB64&list=PLcXyemr8ZeoSGlzhlw4gmpNGicIL4kMcX&index=2
- 15:00 시작 

- encapsulation: ex) application layer ➡️ physical layer 포장 
- decapsulation: ex) physical layer ➡️ application layer 포장 풀기 




