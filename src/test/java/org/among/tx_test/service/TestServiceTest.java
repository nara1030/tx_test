package org.among.tx_test.service;

import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 테스트 코드 작성이 어려워 로그 테스트용으로 사용
 */
@SpringBootTest
class TestServiceTest {
    @Autowired
    private TestService testService;

    /**
     * 트랜잭션은 AOP 기반으로 적용되므로 실제 프록시 객체 적용되어 있는지 확인
     * true: 프록시 적용됨
     * false: 트랜잭션 AOP 적용 안됨
     *
     * false 떠서 main 메소드에 @EnableTransactionManagement 추가
     * 단, 위는 DB 설정 안해줬을 때 얘기고 DB 설정 있으면 트랜잭션 매니저 자동 등록
     * 즉 DataSource 있으면 트랜잭션 매니저 자동 등록
     */
    @Test
    void checkAopProxy() {
        System.out.println("Is AOP proxy? = " + AopUtils.isAopProxy(testService));
    }

    /**
     * @EnableTransactionManagement 추가한 이후 다음 에러 발생
     * No qualifying bean of type 'org.springframework.transaction.TransactionManager' available
     *
     * 트랜잭션 매니저 등록
     * 1) JDBC 기반 트랜잭션 매니저 수동 등록(사용)
     * 2) JPA 환경이라면, JPA 트랜잭션 매니저 등록
     */
    @Test
    void testTransactionIsActive() {
        testService.outer();
    }

    @Test
    void testTransaction1IsActive() {
        System.out.println("TestService bean class: " + testService.getClass());
        testService.outerMethod1();
    }

    @Test
    void testTransaction2IsActive() {
        System.out.println("TestService bean class: " + testService.getClass());
        System.out.println("Is AOP Proxy: " + AopUtils.isAopProxy(testService));
        testService.outerMethod2();
    }

    @Test
    void testTransaction3IsActive() {
        testService.outerMethod3();
    }

    @Test
    void testTransaction4IsActive() {
        testService.outerMethod4();
    }

    @Test
    void testTransaction5IsActive() {
        testService.outerMethod5();
    }
}