package org.among.tx_test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class TestService {
    @Autowired
    private DataSource dataSource;

    @Transactional
    public void outer() {
        boolean active = TransactionSynchronizationManager.isActualTransactionActive();
        System.out.println("OUTER - isActualTransactionActive = " + active);
    }
    public boolean outerMethod1() {
        boolean txActived = TransactionSynchronizationManager.isActualTransactionActive();
        System.out.println("Outer Method1 Transaction Active: " + txActived);
        txActived = innerMethod1();
        System.out.println("Inner Method1 Transaction Active: " + txActived);
        return txActived;
    }

    public boolean innerMethod1() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    @Transactional
    public boolean outerMethod2() {
        boolean txActived = TransactionSynchronizationManager.isActualTransactionActive();
        System.out.println("Outer Method2 Transaction Active: " + txActived);
        txActived = innerMethod2();
        System.out.println("Inner Method2 Transaction Active: " + txActived);
        return txActived;
    }

    public boolean innerMethod2() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    @Transactional
    public boolean outerMethod3() {
        boolean txActived = TransactionSynchronizationManager.isActualTransactionActive();
        System.out.println("Outer Method3 Transaction Active: " + txActived);
        txActived = innerMethod3();
        System.out.println("Inner Method3 Transaction Active: " + txActived);
        return txActived;
    }

    private boolean innerMethod3() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    public boolean outerMethod4() {
        boolean txActived = TransactionSynchronizationManager.isActualTransactionActive();
        System.out.println("Outer Method4 Transaction Active: " + txActived);
        txActived = innerMethod4();
        System.out.println("Inner Method4 Transaction Active: " + txActived);
        return txActived;
    }

    @Transactional
    public boolean innerMethod4() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    @Transactional
    public boolean outerMethod5() {
        boolean txActived = TransactionSynchronizationManager.isActualTransactionActive();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        System.out.println("Outer Method5 Transaction Active: " + txActived);
        System.out.println("Outer Connection hash: " + connection.hashCode());
        txActived = innerMethod5();
        return txActived;
    }

    @Transactional
    public boolean innerMethod5() {
        boolean txActived = TransactionSynchronizationManager.isActualTransactionActive();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        System.out.println("Inner Method5 Transaction Active: " + txActived);
        System.out.println("Inner Connection hash: " + connection.hashCode());
        return txActived;
    }
}
