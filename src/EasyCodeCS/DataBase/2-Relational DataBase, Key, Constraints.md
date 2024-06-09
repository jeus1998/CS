
# Relational DataBase, Key, Constraints

### 수학에서 relation

set
- 서로 다른 elements를 가지는 collection
- 하나의 set에서 elements의 순서는 중요하지 않다.
- ex) {1,3,11,4,7}

![7.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F7.JPG)
- set A select 1 , set B select 1 
- Cartesian product: A X B (pair 만들 수 있는 모든 경우의 수) 


![8.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F8.JPG)
- Cartesian product 부분 집합 
- binary relation

![9.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F9.JPG)
- n - 튜플: 각 집합에서 원소를 1개씩 고른 Cartesian product 부분 집합

relation in mathematics
- subset of Cartesian product
- set - of tuples

### relation in mathematics & relational data model

![10.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F10.JPG)

- 각 집합은 relational data model 에서는 domain 
- relation data model 에서는 각 domain에 이름을 붙인다.
- 또한 domain 집합은 relation

### relational data model 이해하기 

- student relation 예로 들어 relational data model 이해 

domain 정의하기 
- students_ids: 학번 집합, integer
- human_names: 사람 이름 집합, 문자열
- university_grades: 대학교 학년 집합 {1,2,3,4}
- major_names: 전공 이름 집합
- phone_numbers: 핸드폰 번호 집합

attribute 
- 1개의 relation (domain phone_numbers 2개인 경우)
- phone_numbers: 비상 연락망
- phone_numbers: 핸드폰 번호 
- 이렇게 2개의 domain 역할을 구분 해주기 위해서 나온 개념이 attribute(속성)

attribute & tuple
![11.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F11.JPG)

- 이런 데이터들은 테이블로 표시하기 좋다. 

![12.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F12.JPG)

![13.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F13.JPG)

### relation schema

relation schema
- relation 구조를 나타낸다.
- relation 이름과 attribute 리스트로 표기된다.
- ex) STUDENT(id, name, grade, major, phone_num, emer_phone_num)
- attributes 관련 constraints 포함 

degree of relation
- relation schema attributes의 수 
- ex) STUDENT(id, name, grade, major, phone_num, emer_phone_num) ➡️ 6

relation(or relation state)
- 특정 시점에서의 실제 데이터 

![14.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F14.JPG)

### relation database 관계형 데이터 베이스 

- relational data model에 기반하여 구조하된 database
- relational database는 여러 개의 relations로 구성된다.

relational database schema
- relation schemas set + integrity constraints set(제약 조건 집합)

### relation 특징들 

- relation은 중복된 tuple을 가질 수 없다 (relation is set of tuples)
- relation의 tuple을 식별하기 위해 attribute의 부분 집합을 key로 설정
- relation에서 tuple의 순서는 중요하지 않다
- attribute는 atomic 해야 한다(composite or multivalued attribute 허용 ❌)
![15.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F15.JPG)
- 서율특별시, 강남구, 청담동 ➡️ composite attribute ➡️ not atomic 
- 컴공, 디자인 ➡️  multivalued attribute ➡️ not atomic ️


### null 의미
- 값이 존재하지 않는다
- 값이 존재하나 아직 그 값이 무엇인지 알지 못한다
- 해당 사항과 관련이 없다

## key 설명

### super key

- relation에서 tuples를 unique하게 식별할 수 있는 attributes set

PLAYER(id, name, team_id, back_number, birth_date) super key?
- {id, name, team_id, back_number, birth_date}
- {id, name} 
  - id는 선수의 고유한 번호여서 id를 포함하면 항상 super key
- {name, team_id, back_number}
  - 같은 팀에서 등번호는 고유하게 존재한다. ➡️ super key

### candidate key

- 어느 한 attribute라도 제거하면 unique하게 tuples를 식별할 수 없는 super key
- key or minimal super key

PLAYER(id, name, team_id, back_number, birth_date) candidate key?
- {id}
- {team_id, back_number}

### primary key

- relation에서 tuples를 unique하게 식별하기 위해 선택된 candidate key

PLAYER(id, name, team_id, back_number, birth_date) primary key?
- {id}
- {team_id, back_number}
- 보통 attribute 개수가 적은 candidate key를 primary key로 선택한다
  - {id} ➡️ primary key

### unique key

- primary key가 아닌 candidate keys
- alternate key

PLAYER(id, name, team_id, back_number, birth_date) unique key?
- {team_id, back_number}

### foreign key

- 다른 relation의 pk를 참조하는 attributes set

- PLAYER(id, name, team_id, back_number, birth_date)
- TEAM(id, name, manager)
- foreign key: PLAYER{team_id}
  - TEAM(id)를 참조한다.

## constraints 

- relational database의 relations들이 언제나 항상 지켜줘야 하는 제약 사항

### implicit constraints 

- relational data model 자체가 가지는 constraints
- relation은 중복되는 tuple을 가질 수 없다.
- relation 내에서는 같은 이름의 attribute를 가질 수 없다.

### schema-based constraints 

- 주로 DDL을 통해 schema에 직접 명시할 수 있는 constraints
- explicit constraints

### domain constraints

- attribute value는 해당 attribute의 domain에 속한 value여야 한다.

![16.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F16.JPG)
- grade ➡️ 1 ~ 4 

### key constraints

- 서로 다른 tuples는 같은 value의 key를 가질 수 없다.

![17.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F17.JPG)

### NULL value constraints

- attribute가 NOT NULL로 명시됐다면 NULL을 값으로 가질 수 없다.

### entity integrity constraint

- primary key는 value에 NULL을 가질 수 없다.

### referential integrity constraint

- FK와 PK와 도메인이 같아야 하고 PK에 없는 values를 FK가 값으로 가질 수 없다.

![18.JPG](%EC%9D%B4%EB%AF%B8%EC%A7%80%2F18.JPG)


