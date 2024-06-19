# Priority Queue & Heap 

### PriorityQueue

- 우선순위 큐 
- 큐와 유사하지만 우선순위가 높은 아이템이 먼저 처리됨 
- 주요 동작 
  - add
  - poll
  - peek
- 시간 복잡도
  - 원소의 추가 O(log N)
  - 우선순위가 가장 높은 원소의 확인 O(1)
  - 우선순위가 가장 높은 원소 제거 O(log N)

### Heap

- 힙
- 이진 트리 자료 구조 기반 
- 이진 트리: 각 정점의 자식이 2개인 트리 
- 최댓값 혹은 최솟값을 찾는 목적으로 사용 가능 
  - 최대 힙 : 최댓값을 찾기 위해 사용한는 힙
  - 최소 힙 : 최솟값을 찾기 위해 사용하는 힙

![1.png](Image%2F1.png)
- 최소 힙에서는 부모가 자식 보다 작아야 한다. → 루트가  최솟값 
- 최대 힙은 부모가 자식보다 커야 한다. → 루트가 최댓값 

### Heap 삽입 순서 

![2.png](Image%2F2.png)

- 이런 순서로 값을 채워 나간다. 
- 이진 검색 트리와 다르게 힙은 불균형이 발생하지 않고 늘 균형이 맞는 균형 이진 트리의 모양 

### 최소 Heap (삽입)Insert Simulation 

- 35 삽입 

![3.png](Image%2F3.png)

- 55 삽입

![4.png](Image%2F4.png)

- 15 삽입 

![5.png](Image%2F5.png)

- 35와 15에서 부모와 자식의 관계가 최소힙의 조건에 위배 (부모 < 자식)
- 부모와 자식의 자리를 바꾼다. 
- 35 🔄 15

![6.png](Image%2F6.png)

- 8 삽입

![7.png](Image%2F7.png)

- 마찬가지로 최소힙의 조건에 위배 
- 8 🔄 55

![8.png](Image%2F8.png)

- 8 🔄 15

![9.png](Image%2F9.png)

- 32 삽입

![10.png](Image%2F10.png)

- 매번 삽입을 할 때 마다 아무리 비교를 많이 해도 최대 높이 만큼만 비교를 하고 문제가 있으면 
  올라가면서 자리를 바꿔주면 끝난다.
- 힙의 구조상 균형 트리이기 때문에 삽입의 시간 복잡도는 O(log N)이다.

### Heap Find

- 최솟값 확인은 매우 간단하다. 그냥 루트의 값이 최솟값이다. 
- 단 최소 힙에서는 최솟값을 효율적으로 확인할 수 있지만, 열 번째로 작은 값의 확인이라던가 
  최댓값의 확인은 모든 원소를 다 보는게 아닌 이상 불가능하다.
- 이런 특징은 이진 검색 트리와 힙의 차이다. 

### Heap Erase 

![11.png](Image%2F11.png)

- 최솟값을 지우는 연산, 여기서는 루트(8)

![12.png](Image%2F12.png)

- 트리의 구조를 지키기 위해서 트리의 순서상 가장 마지막인 원소와 자리 교체를 하고 8을 없앤다. 
- 여기서 8을 없애도 트리의 구조가 지켜지는 이유는 가장 마지막인 원소는 자식이 없기 때문이다. 
- 이제 52가 루트로 왔으니 다시 최소 힙의 조건을 맞춘다. 

![13.png](Image%2F13.png)

- 12, 13, 52 중에서 가장 작은 값은 12니까 12 🔄 52 바꾼다.

![14.png](Image%2F14.png)

- 52, 16, 16에서는 어떻게 최소 힙 조건을 맞출까?
- 16, 16 둘 중 어느 것을 올리더라도 문제가 없다! 

![15.png](Image%2F15.png)

- 52 🔄 16

![16.png](Image%2F16.png)

- 52 🔄 22 
- 끝

### 자바 PriorityQueue 계층 구조 

![17.png](Image%2F17.png)

### 이진 검색 트리 기반 vs heap 기반 

- TreeSet과 PriorityQueue를 비교해 보자
- PriorityQueue에서 수행 가능한 기능은 TreeSet에서 모두 수행 가능하고 훨씬 더 다양한 기능을 제공하다.
    - ex) NavigableSet(인터페이스) 메서드
- 삽입, 삭제, 검색 시간 복잡도 또한 동일하다
    - TreeSet의 검색은 평균이 O(lg N)
    - first(), last() 는 O(1)이다.

```text
다 맞는 말이다. 하지만 PriorityQueue가 똑같은 데이터 셋을 수행할 때 TreeSet 보다 더 빠르고 공간도 적게 차지한다. 빅오 표기법(O)로 하는건 평균적인 수치이다 . 너무 맹신하지 말자 

이런 수행 시간 차이가 나는건 이진 검색 트리는 트리 불균형에 대한 균형을 맞추기 위한 시간이 소비된다. (자바는 자가 균형 트리인 레드 블랙 트리) 

하지만 PriorityQueue의 이진 트리는 불균형이 존재하지 않는다. 항상 삽입 순서가 정해져 있다.

언제나 O(lg N)에 수행 

같은 연산을 할 때 비교를 해보면 속도가 2~4배 정도 차이 난다고 한다. 

그래서 PriorityQueue에서 제공하는 연산으로만 해결 가능한 데이터라면 

PriorityQueue를 사용하는 게 좋다!!
```

### Priority Queue & Heap 관계

- Priority Queue = ADT
- Heap = Data Structure

### 우선순위 큐 사용 사례

- 프로세스 스케줄링(process scheduling): ready queue 
- heap sort (힙 정렬)
- ✅힙 메모리의 힙과 자료 구조의 힙은 관련이 없다. 
