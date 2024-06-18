# 변수, 객체 메모리의 관계 & 자바의 메모리 영역, 프로그램 Execution Stages

### 애플리케이션은 어떻게 실행되는가?

- 애플리케이션
  - 일반 사용자가 사용할 기능을 제공하는
  - 컴퓨터가 실행할 수 있는 명령어들의 집합 
- 메모리
  - 실행된 애플리케이션이 상주하는 곳
  - 실행되면서 생기는 데이터들의 저장 장소 
- CPU
  - 명령어를 실행하는 주체 

### 자바 기준 메모리 구조 (언어마다 다르다)

- 애플리케이션에 할당되는 메모리는 내부적으로 여러 영역으로 나뉨 
  - stack 메모리, heap 메모리 
- stack 메모리
  - 함수나 메서드의 지역 변수(local variable)와 매개 변수(parameter)가 저장됨 
  - 함수나 메서드가 호출될 때 마다 스택 프레임(stack frame)이 쌓임 
  - 각 스레드마다 하나의 스택이 존재 
- heap 메모리 
  - 객체가 저장됨 (모든 인스턴스 변수)
- 메소드 영역(Method area)
  - 클래스에 대한 메타데이터(클래스 정보, 메서드 코드, 상수 풀, 정적 변수) 저장 
  - 모든 스레드가 공유하는 영역 
- 네이티브 메서드 스택(Native Method Stack)
  - 자바가 아닌 다른 언어로 작성된 네이티브 메서드 호출 시 사용되는 메모리 영역 

### Static Variable & Static Method 

- static 변수 
  - static 키워드로 선언된 변수는 클래스 로딩 시 메소드 영역에 저장 
  - 클래스 로더에 의해 로드, 프로그램 종료될 때까지 존재
  - 모든 인스턴스가 이 변수를 공유 
- static 메서드 
  - static 키워드로 선언된 메서드 코드 역시 메소드 영역에 저장 
  - 호출 시(runtime)에는 스택 메모리에 메서드 프레임(스택 프레임)이 생성 

### 프로그램 실행 단계 (Execution Stages)

- 컴파일 타임 (Compile Time)
  - 소스 코드가 바이트코드(.class)로 컴파일 되는 단계 
- 로딩 타임(Loading Time)
  - JVM이 클래스를 메모리에 로드하는 단계
  - 메소드 영역에 클래스 메타데이터 및 static 멤버가 적재되는 시점 
- 런타임(Runtime)
  - 프로그램이 실제로 실행되어 메서드가 호출되고, 스택 프레임이 생성되는 등 동작이 이루어지는 단계 

### Stack 메모리 
![1.jpg](Image%2F1.jpg)


![2.jpg](Image%2F2.jpg)

정리 
- 스택 메모리는 메서드의 호출과 종료를 반복하면서 스택 프레임이 생겼다가 사라졌다가를 자동으로 반복한다. 
- 스택 메모리 초과가 발생하는 이유는 대부분 재귀 함수에 종료 시점 설정을 이상하게 해서 스택 프레임이 계속 
  재귀적으로 쌓이다가 스택 메모리 초과로 프로그램이 죽는 case가 있다. 

### Heap 메모리 

```java
public class Main{
    public static void main(String[] args) {
        Counter c = new Counter();
    }
}

public class Counter{
    private int state = 0;
    public void increment(){ state++; }
    public void get() {return state; }
}
```
![3.jpg](Image%2F3.jpg)

```java
public class Main{
    public static void main(String[] args) {
        Counter c = new Counter();
        two(c);
        int count = c.get();
    }
    public static void two(Counter c){
        c.increment();
        c.increment();
    }
}

public class Counter{
    private int state = 0;
    public void increment(){ state++; }
    public void get() {return state; }
}
```
- new Counter()를 통해서 객체를 생성할 때 스택 프레임이 생성되는데 이 스택 프레임에는 this라는 변수가 있고 
  this 변수는 생성한 객체의 주솟값을 가진다. 
- two(Counter c)메서드를 보면 매개변수로 Counter 객체의 주솟값을 가지는 변수가 있다. 
- two 메서드에서 ``c.increment()``를 2번 하는데 
- 당연히 increment() 또한 메서드여서 스택 프레임이 생성된다.
- 이때 increment() 메서드의 스택 프레임에는 this라는 변수가 있는데 이 this라는 변수는 heap 메모리에 저장된 
  객체의 주솟값을 가진다.

### 쓰레기 객체 Garbage Object

```java
public class Main{
    public static void main(String[] args) {
        Counter c = make();
    }
    public static Counter make(){
        Counter c = new Counter();
        return new Counter();
    }
}

public class Counter{
    private int state = 0;
    public void increment(){ state++; }
    public void get() {return state; }
}
```

- make()메서드를 보면 ``Counter c = new Counter``를 통해서 객체를 생성하고 c(reference variable)에 저장을 한다.
- 그런데 make()스택 프레임에서 생성되어서 힙 메모리에 저장되는 이 객체는 make() 스택 프레임이 종료되면 접근할 방법이 없다.
- 이런 접근 불가능한 객체를 쓰레기 객체라고 한다. Garbage Object
- 자바에서는 이런 쓰레기 객체를 언어 레벨에서 Garbage Collector를 통해서 청소를 한다. 
- 가비지 컬렉터의 효율성과 성능은 프로그램의 전체 성능에 영향을 미칠 수 있다. 
- GC가 효율적으로 작동하면 메모리 관리가 원활하게 이루어져 프로그램 성능이 향상
- 반대로, GC가 비효율적으로 작동하면 메모리 누수나 불필요한 메모리 사용이 발생할 수 있다.

