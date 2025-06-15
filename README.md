## 트랜잭션 테스트

```text
스프링 환경에서 서버 소스를 작업하며 주로 서비스단에 로직을 작성해왔다.
이때 한 메소드에 길게 작성하는 것보다 헬퍼 메소드(내부 메소드)를 작성해서 가독성을 높여왔는데,
몇 가지 간단한 예를 통해 트랜잭션 적용 여부를 테스트해보았다.

테스트 코드(TestServiceTest)로 개별 로직을 실행만 하였고, 트랜잭션 적용 여부는 로그를 통해 확인하였다.
트랜잭션은 AOP 기반으로 적용되므로 먼저 간단한 테스트를 통해 프록시 여부를 확인해주었다.
(checkAopProxy, testTransactionIsActive)

※
트랜잭션 적용을 위해 세 가지 의존성을 추가해주었다.

implementation 'org.springframework:spring-tx'
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
runtimeOnly 'com.h2database:h2'

JDBC 의존성만 추가하면 트랜잭션 관련 클래스는 다 존재하지만 DataSource가 생성되지 않는다.
(JDBC 의존성이 @EnableTransactionManagement도 활성화한다고 한다.)
실제 데이터베이스 구현체(H2) 의존성을 추가해야,
DataSource를 자동 생성 후, 이를 통해 DataSourceTransactionManager를 자동 등록해준다.
이를 통해 @Transactional이 작동하기 때문에 필수로 추가해주어야 한다.

※
TransactionSynchronizationManager.isActualTransactionActive()
↑ 트랜잭션 활성화 여부는 알 수 있지만, 같은 트랜잭션인지는 확인 불가
innerMethod5 메소드처럼 (outerMethod5 메소드와) 같은 클래스 내에서 호출하면,
스프링의 트랜잭션 프록시를 타지 않고 메소드가 실행되므로 innerMethod5 메소드의 @Transactional은 무시된다.
즉, innerMethod5 메소드가 새 트랜잭션을 여는 것이 아니라 이미 존재하는 트랜잭션 안에서 실행된 것이다.
하지만 확인을 위해 아래 코드를 추가해주었고, 그 결과는 하단 이미지와 같다.
Connection connection = DataSourceUtils.getConnection(dataSource);

※ CGLIB과 JDK 동적 프록시
스프링은 트랜잭션 처리나 AOP 기능을 프록시 기반으로 제공하며,
클래스가 인터페이스를 구현하면 JDK 프록시, 그렇지 않으면 CGLIB를 사용한다.
```

<img src="/img/img_01.png" width="350" height="200">