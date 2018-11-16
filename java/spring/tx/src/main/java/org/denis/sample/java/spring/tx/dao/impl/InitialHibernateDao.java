package org.denis.sample.java.spring.tx.dao.impl;

import org.denis.sample.java.spring.tx.dao.Dao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.denis.sample.java.spring.tx.di.DiConstants.*;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Component
public class InitialHibernateDao implements Dao {

    private final SessionFactory roSessionFactory;
    private final SessionFactory rwSessionFactory;

    @Autowired
    public InitialHibernateDao(@Qualifier(SESSION_FACTORY_RO) SessionFactory roSessionFactory,
                               @Qualifier(SESSION_FACTORY_RW) SessionFactory rwSessionFactory)
    {
        this.roSessionFactory = roSessionFactory;
        this.rwSessionFactory = rwSessionFactory;
    }

    @Transactional(transactionManager = HIBERNATE_TX_MANAGER_RW)
    @Override
    public void insert(long id, long data) {
        rwSessionFactory.getCurrentSession()
                        .createNativeQuery("insert into test (id, data) values (" + id + ", " + data + ")")
                        .executeUpdate();
    }

    @Transactional(transactionManager = HIBERNATE_TX_MANAGER_RO, readOnly = true, isolation = READ_COMMITTED)
    @Override
    public long getData(long id) {
        return ((Number) roSessionFactory.getCurrentSession()
                                         .createNativeQuery("select data from test where id = " + id)
                                         .uniqueResult()).longValue();
    }

    @Transactional(transactionManager = HIBERNATE_TX_MANAGER_RW, isolation = READ_COMMITTED)
    @Override
    public void update(long id, long data) {
        rwSessionFactory.getCurrentSession()
                        .createNativeQuery("update test set data = " + data + " where id = " + id)
                        .executeUpdate();
    }
}
