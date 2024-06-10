
# stored procedure2(stored procedure 의미, 장단점)

### 3-tier architecture - stored procedure 의미 

```text
오늘날의 IT 회사들은 일반적으로 
client-server architecture의 한 종류인 
three-tier architecture 모델로 서비스를 개발한다 
```
![55.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F55.JPG)

![56.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F56.JPG)

- 당근마켓의 logic tier(비즈니스 로직)
  - 회원가입/탈퇴
  - 상품 리스트업 알고리즘
  - 상품 정보 업로드 기능
  - 상품 검색 기능
  - 메시지 기능
- 당근마켓의 data tier(데이터)
  - 회원 정보
  - 상품 정보
  - 판매/구매 내역 
  - 지역 정보 

stored procedure 의미
- RDBMS에 저장되고 사용되는 프로시저
- 주된 사용 목적은 비즈니스 로직 구현
- stored procedure의 존재 의미 ➡️ logic tier와 data tier 모두 비즈니스 로직이 존재 


## stored procedure 장점

### application에 transparent 하다 
- transparent: 투명한, 명백한, 솔직한 

![57.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F57.JPG)
- logic tier 에서 비즈니스 로직에 변경이 일어났다(가정)
- 그럼 전체 logic tier 애플리케이션을 내리고 변경하는게 아닌 
- 하나씩 애플리케이션을 내리고 수정을 하고 빌드를 한 이후에 다시 배포를 한다. 
  - 한번에 모든 애플리케이션을 내리면 서비스 전체가 중단

![58.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F58.JPG)
- 그림처럼 로직이 data tier(procedure)에 있다면?
- 애플리케이션(logic tier)에서는 호출만 한다. 
- procedure 파라미터가 바뀌는게 아니라면 body 부분만 변경 
- transparent, simple

### network traffic을 줄여서 응답 속도를 향상

![59.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F59.JPG)

![60.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F60.JPG)
- logic이 RDBMS에 있는 경우 
- 애플리케이션 서버에서는 RDBMS의 프로시저를 호출 
- SELECT, INSERT, UPDATE 요청이 RDBMS 내부에서 동작 
- ➡️ network traffic이 줄어든다.

### 여러 서비스에서 재사용 가능

![61.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F61.JPG)
- 서비스A(카카오톡), 서비스B(카카오 페이), 서비스C(카카오 스토리) 가정
- 서비스A, 서비스B, 서비스C 모두 동일 로직 
- 서비스A: JAVA-SPRING
- 서비스B: python-django
- 서비스C: JS-Node.js
- 동일한 로직인데 모두 다른 언어,프레임워크로 구현 
- ➡️ 비효율적 


![62.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F62.JPG)
- 각각의 서비스는 RDBMS의 procedure를 호출만 해주면 끝
- ➡️ 여러 서비스에서 재사용 가능

### 민감한 정보에 대한 접근을 제한 

- 개발자가 DB에 저장된 민감한 정보(주민번호) 직접 접근은 ❌
- 프로시저를 통한 간접 접근은 허용 ⭕️
- ➡️ 민감한 정보에 대한 직접 접근을 막는다

## stored procedure 단점 & 실무에서 쓰기에 조심스러운 이유

### 유지 관리 보수 비용이 커진다

- 비즈니스 로직이 분리 ➡️ 로직 분석 비용 증가 
- logic tier(소스코드), data tier(프로시저) 버전 관리를 모두 해야함
- procedure 관련 공부를 해야함
- 신규 기능 개발 시 
  - procedure RDBMS 재배포 
  - 해당 procedure 호출 하는 애플리케이션 또한 재배포

### DB 서버를 추가하는 것은 간단한 작업이 ❌

- 트래픽이 갑자기 늘어났다고 가정(치킨 디도스, 이벤트 등등..)
- 비즈니스 로직이 DB에 있으면 안그래도 많은 애플리케이션 서버가 DB에 많은 요청을 하는데
- 더 부하가 많이 걸린다.
- 이때 DB 서버를 추가 한다고 해도 바로 사용 ❌
  - 데이터를 복사 해야만 사용 가능 
- 이런 이유로 DB 서버를 추가 ➡️ 어려운 작업 
- 반대로 애플리케이션 서버(logic tier)서버 추가는 간단 
  - 클라우드 (오토 스케일링)

### stored procedure가 언제나 transparent인건 아니다

- procedure body 부분이 아닌 파라미터나 이름이 바뀐다면?
- logic tier에서 호출이 불가능 ❌
- 기존 procedure를 냅두고 새로운 proceduer를 만든 다음에 logic tier의 호출 부분을 수정해야함
  - ➡️ transparent ❌

### transparent 하다고 무조건 좋은 것도 아니다

- procedure 로직을 수정 하다가 에러가 발생(가정)
- 여러 애플리케이션 서버는 DBMS의 프로시저에 모두 영향을 받는다
- 만약 logic이 logic tier에 있었다면?
- 1대의 애플리케이션 서버에서 수정을 하고 문제가 있으면 바로 롤백 
   - 나머지 애플리케이션 서버는 영향 ❌
- 전부 영향 VS 부분 영향 
  - 예상치 못한 문제의 영향을 최소화할 수 있다.

### 재사용 가능하다는 것이 양날의 검 

- 만약 해당 프로시저를 구현한 DB에 문제가 생기면 
- 해당 프로시저를 호출하는 모든 서비스에 영향
   - 서비스A, 서비스B, 서비스C

![63.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F63.JPG)

![64.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F64.JPG)
- 이렇게 Architecture를 구성하면 서비스 A만 영향 

### ⭐️ 비즈니스 로직을 소스 코드에 두고도 응답 속도를 향상 시킬 수 있다.

![65.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F65.JPG)
- 동시에 요청하기(순서가 필요 없다면)
  - non block I/O
  - 쓰레드풀 

![66.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F66.JPG)

![67.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F67.JPG)
- 처음 요청은 약간의 트래픽이 더 필요(캐시 미스)
- 하지만 캐시(레디스)에 요청 데이터가 있다면(캐스 히트) 응답 속도 향상
  - 추가로 DB 부하도 줄인다

### Else
- procedure로는 복잡하고 유연한 코드를 작성하기 어렵다
- 오늘날의 프로그래밍 언어는 훨씬 다양하고 강력한 기능을 제공 
- procedure는 가독성이 떨어진다 
- procedure는 디버깅이 어렵다 




