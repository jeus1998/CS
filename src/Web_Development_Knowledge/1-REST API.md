# REST API 정리

REST API란? ➡️ REST를 기반으로 만들어진 API

### REST

- REST(Representational State Transfer)
- 자원을 이름으로 구분하여 해당 자원의 상태를 주고받는 모든 것
- HTTP URI(Uniform Resource Identifier)를 통해 자원(Resource)을 명시
- HTTP Method(POST, GET, PUT, DELETE, PATCH 등)를 통해 해당 자원(URI)에 대한 CRUD Operation을 적용하는 것을 의미

### REST 구성 요소

- 자원(Resource): HTTP URI
- 자원에 대한 행위(Verb): HTTP Method
- 자원에 대한 행위의 내용 (Representations): HTTP Message Pay Load

### REST 특징 

- Server-Client(서버-클라이언트 구조)
- Stateless(무상태)
- Cacheable(캐시 처리 가능)
- Layered System(계층화)
- Uniform Interface(인터페이스 일관성)

### REST 장단점

장점⭕️
- HTTP 프로토콜의 인프라를 그대로 사용 
  - REST API 사용을 위한 별도의 인프라를 구출할 필요❌
- HTTP 표준 프로토콜에 따르는 모든 플랫폼에서 사용이 가능
- REST API 메시지가 의도하는 바를 명확하게 나타내므로 의도하는 바를 쉽게 파악
- 여러 가지 서비스 디자인에서 생길 수 있는 문제를 최소화
- 서버와 클라이언트의 역할을 명확하게 분리

단점❌
- HTTP Method 형태가 제한적
- 표준이 자체가 존재하지 않아 정의가 필요
- 브라우저를 통해 테스트할 일이 많은 서비스라면 쉽게 고칠 수 있는 URL보다 Header 정보의 값을 처리해야 하므로 전문성이 요구
- 구형 브라우저에서 호환이 되지 않아 지원해주지 못하는 동작이 많다

### API

- API(Application Programming Interface) 
- API는 정의 및 프로토콜 집합을 사용하여 두 소프트웨어 구성 요소가 서로 통신할 수 있게 하는 메커니즘 
  - EX) 기상청의 소프트웨어 시스템에는 일일 기상 데이터가 있다. 휴대폰의 날씨 앱은 기상청의 일일 기상 API를 통해서
    해당 시스템과 통신을 하여 휴대폰에 최신 날짜 정보를 표시
- API 종류
  - RPC API
  - REST API
  - WebSocket API
  - SOAP API

### REST API

- REST API?
  - REST 원리를 따르는 API 
- ✅REST API 설계 예시 
  - URI는 동사보다는 명사를, 대문자보다는 소문자를 사용
    - Bad Example http://google.com/Running/
    - Good Example  http://google.com/run/  
  - 마지막에 슬래시 (/)를 포함하지 않는다.
    - Bad Example http://google.com/test/  
    - Good Example  http://google.com/test
  - 언더바 대신 하이폰을 사용한다.
    - Bad Example http://google.com/test_blog
    - Good Example  http://google.com/test-blog  
  - 파일확장자는 URI에 포함하지 않는다.
    - Bad Example http://google.com/photo.jpg  
    - Good Example  http://google.com/photo  
  - 행위를 포함하지 않는다.
    - Bad Example http://google.com/delete-post/1  
    - Good Example  http://google.com/post/1  

### RESTful

- RESTFUL이란 REST의 원리를 따르는 시스템을 의미
- REST를 사용했다 하여 모두가 RESTful 한 것은❌
- REST API의 설계 규칙을 올바르게 지킨 시스템을 RESTful하다 말할 수 있다.

### 참고 자료(References)

- [AWS/what-is/api](https://aws.amazon.com/ko/what-is/api/)
- [히진쓰의 서버사이드 기술 블로그](https://khj93.tistory.com/entry/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-REST-API%EB%9E%80-REST-RESTful%EC%9D%B4%EB%9E%80)