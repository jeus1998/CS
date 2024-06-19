# Deadlock 교착상태

### Deadlock?

![14.png](Image%2F14.png)

- 두 개 이상의 프로세스 혹은 스레드가 서로가 가진 리소르를 기다리는 상태

### Deadlock 조건 

- Mutual exclusion 
  - 리소스(resource)를 공유해서 사용할 수 없다. 
- Hold and wait
  - 프로세스가 이미 하나 이상의 리소스를 취득한(hold) 상태에서 다른 프로세스가 사용하고 있는 리소스를 
    추가로 기다린다(wait)

![15.png](Image%2F15.png)

- No preemption
  - 리소스 반환(release)은 오직 그 리소스를 취득한 프로세스만 할 수 있다. 
- Circular wait
  - 프로세스들이 순환(circular) 형태로 서로의 리소스를 기다린다 

### OS가 데드락 해결 방법 

1. 데드락 방지
2. 데드락 회피
3. 데드락 감지와 복구
4. 데드락 무시 

### 데드락 방지 (Deadlock prevention)

```text
네 가지 조건 중 하나가 충족되지 않게 시스템을 디자인 
```
- 데드락 방지 #1 mutual exclusion
  - 리소스를 공유 가능하게 함 
  - 현실적으로 불가능 리소스 상호 배제는 이유가 있어서 한다. 
  - ex) 프린터기는 동시에 2개의 작업을 못한다.
  - 동기화 문제로 인한 불가능 
- 데드락 방지 #2 hold and wait 
  - 사용할 리소스들을 모두 획득한 뒤에 시작 
  - 리소스를 전혀 가지지 않은 상태에서만 리소스 요청 
  - 리소스가 인기가 많은 리소스 면 얻는 데 너무 오래 걸린다. 
  - 만약 2개의 리소스를 사용하는데 1개의 리소스 사용이 오래 걸린다고 하면 나머지 1개는 놀게 된다.
- 데드락 방지 #3 No preemption
  - 추가적인 리소스를 기다려야 한다면 이미 획득한 리소스를 다른 프로세스가 선점 가능하도록 한다 
  - 모니터의 방식과 유사 
    - 모니터에서는 condition variable 조건을 통과 하지 못하면 가지고 있는 락(mutex)을 반환 하고
      waiting queue에 들어간다. 
- 데드락 방지 #4 circular wait
  - 모든 리소스에 순서 체계를 부여해서 오름차순으로 리소스를 요청 
  - ![16.png](Image%2F16.png)
  - 그림처럼 1번 리소스를 먼저 요청하고 4번 리소스를 요청하게 만든다. 
  - 데드락 방지 4가지 방법중에 가장 많이 사용하는 방법 

### 데드락 회피 (Deadlock avoidance)

```text
 실행 환경에서 추가적인 정보를 활용해서 
 데드락이 발생할 것 같은 상황을 회피하는 것 
 
 추가적인 정보란?
 미래에서 사용할 리소스, 현재 사용중인 리소스 등등..
```

Banker Algorithm
```text
Banker's 알고리즘은 다익스트라(Dijkstra)가 제안한 기법으로 교착상태 회피 알고리즘
이 알고리즘은 여러 프로세스들이 공유하는 한정된 자원을 안전하게 할당하기 위해 '은행원'이 고객에게 대출을 해주는 방법에 비유하여 개발
은행원은 고객이 요구하는 최대 자원의 양을 미리 알고 있고, 실제로 자원을 할당할 때에는 시스템이 안전한 상태를 유지할 수 있도록 결정

안전 상태(Safe State) & 불안전 상태(Unsafe State)를 구분 
안전 상태: 모든 프로세스가 교착상태에 빠지지 않고 자원을 순차적으로 할당 받아 완료될 수 있는 상태

은행가 알고리즘 주요 요소
Available (사용 가능한 자원 벡터): 각 자원의 현재 사용 가능한 수량을 나타내는 벡터
Max (최대 요구 벡터): 각 프로세스가 필요로 하는 자원의 최대 수량을 나타내는 행렬
Allocation (할당된 자원 벡터): 각 프로세스에 현재 할당된 자원의 수량을 나타내는 행렬
Need (추가 필요 자원 벡터): 각 프로세스가 완료되기 위해 추가로 필요로 하는 자원의 수량을 나타내는 행렬
Need = Max - Allocation

알고리즘 진행 순서 

1. 안전성 검사 (Safety Check)

시스템이 요청을 승인한 후에도 안전 상태를 유지할 수 있는지 확인

1-1 작업 가능한 임시 벡터 초기화 (Work = Available)
1-2 Finish 벡터 초기화 (Finish[i] = false)
1-3 모든 프로세스에 대해 Finish[i] = false & Need[i] <= Work 인 프로세스를 찾는다. 
1-4 해당 프로세스를 찾으면 Work = Work + Allocation[i]로 업데이트 Finish[i] = True 설정 
1-5 모든 프로세스 해당 작업 반복 모든 프로세스에 대해 Finish[i] = True가 되면 시스템은 안전 상태에 있다고 판정 

2. 자원 요청 처리 (Resource Request Processing)

2-1 요청이 Need 벡터보다 크면, 에러를 반환
2-2 요청이 Available 벡터보다 크면, 프로세스는 대기 상태
2-3 자원을 가정할당하여 시스템이 여전히 안전 상태를 유지하는지 확인
    안전 상태를 유지할 수 있으면 자원을 할당
    그렇지 않으면 프로세스는 대기 상태

예제

Available: [3, 3, 2]

Max
[7, 5, 3]
[3, 2, 2]
[9, 0, 2]
[2, 2, 2]
[4, 3, 3]

Allocation
[0, 1, 0]
[2, 0, 0]
[3, 0, 2]
[2, 1, 1]
[0, 0, 2]

Need(Max - Allocation)
[7, 4, 3]
[1, 2, 2]
[6, 0, 0]
[0, 1, 1]
[4, 3, 1]

P1 프로세스가 [1, 0, 2] 자원을 요청

1. 요청이 Need[1]보다 작거나 같은지 확인   [check]
2. 요청이 Available보다 작거나 같은지 확인 [check]
3. 가정할당 

Available = [3-1, 3-0, 2-2] = [2, 3, 0]
Allocation[1] = [2+1, 0+0, 0+2] = [3, 0, 2]
Need[1] = [1-1, 2-0, 2-2] = [0, 2, 0]

4. 안정섬 검사: 이 가정 할당 후 시스템이 안전 상태인지 확인
```

면접에서 예상되는 질문
```text
Banker's 알고리즘을 어떻게 구현하나요?
이 질문에 대한 대답으로는 위의 작동 원리에 대한 설명과 함께 간단한 코드 예시를 들 수 있습니다. 또한, 실제 알고리즘 구현 시 어떻게 자원의 가용성을 추적하고 프로세스의 요청을 검증하는지를 설명해야 합니다.

교착상태와 Banker's 알고리즘의 관계는 무엇인가요?

교착상태 회피와 관련된 질문입니다. Banker’s 알고리즘이 교착상태를 회피하기 위해 어떻게 설계되었는지, 안전 상태 판단을 통하여 프로세스에 자원을 할당하는 방법을 설명해야 합니다.

안전 상태(Safe State)와 불안전 상태(Unsafe State)의 차이는 무엇인가요?

안전 상태는 시스템이 교착상태에 빠지지 않고 모든 프로세스가 요구하는 자원을 할당받아서 실행을 완료할 수 있는 상태를 의미합니다. 반대로 불안전 상태는 그렇지 못한 상태입니다.

Banker's 알고리즘의 단점은 무엇인가요?

Banker's 알고리즘을 사용하는 과정에서는 최대 자원 요구량을 미리 알아야 하고, 동적인 자원 요청에 대응하기 어렵다는 단점이 있습니다. 또한 복잡한 시스템에서는 계산 오버헤드가 클 수 있습니다.
```

### 데드락 감지와 복구 

- 데드락을 허용하고 데드락이 발생하면 복구하는 전략 
- 복구 전략 
  - 프로세스 종료 (하나씩) 리스크가 크다 
  - 리소스의 일시적인 선점을 허용한다. 

### 데드락 무시

- 개발자가 알아서 하겠지 

### 자바로 살펴보는 프로그래밍 레벨에서 데드락 

```java
public class DeadlockTest {
    public static void main(String[] args) throws InterruptedException{
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread t1 = new Thread(()->{
            synchronized (lock1){
                System.out.println("[t1] get lock1");
                synchronized (lock2){
                    System.out.println("[t1] get lock2");
               }
            }
        });
        
        Thread t2 = new Thread(()->{
           synchronized (lock2){
               System.out.println("[t2] get lock2");
               synchronized (lock1){
                    System.out.println("[t2] get lock1");
               }
           }
       });

       t1.start();
       t2.start();

       t1.join();
       t2.join();
       System.out.println("완료");
    }
}
```

결과
```text
[t2] get lock2
[t1] get lock1
```
- t2 스레드가 lock2를 획득한 시점에 t1 스레드가 lock1을 획득하여 서로가 가진 lock을 기다리는 교착 상태 

해결 방법1
```java
public class DeadlockTest {
    public static void main(String[] args) throws InterruptedException{
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread t1 = new Thread(()->{
            synchronized (lock1){
                System.out.println("[t1] get lock1");
            }
            synchronized (lock2){
                System.out.println("[t1] get lock2");
            }
        });
        
        Thread t2 = new Thread(()->{
           synchronized (lock2){
               System.out.println("[t2] get lock2");
           }
           synchronized (lock1){
              System.out.println("[t2] get lock1");
          }
       });

       t1.start();
       t2.start();

       t1.join();
       t2.join();
       System.out.println("완료");
    }
}
```
- 중첩된 부분을 분리해서 hold and wait를 제거함으로서 교착상태 조건을 없앤다. 


### 참고자료
- [대학원생 개발자의 일상](https://gr-st-dev.tistory.com/2849)
- [유투브 쉬운코드](https://www.youtube.com/watch?v=ESXCSNGFVto&list=PLcXyemr8ZeoT-_8yBc_p_lVwRRqUaN8ET&index=14)