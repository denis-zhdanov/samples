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
public class AdaptiveHibernateDao implements Dao {

    private final SessionFactory sessionFactory;

    @Autowired
    public AdaptiveHibernateDao(@Qualifier(ADAPTIVE_SESSION_FACTORY) SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(transactionManager = ADAPTIVE_HIBERNATE_TX_MANAGER)
    @Override
    public void insert(long id, long data) {
        sessionFactory.getCurrentSession()
                      .createNativeQuery("insert into test (id, data) values (" + id + ", " + data + ")")
                      .executeUpdate();
    }

    @Transactional(transactionManager = ADAPTIVE_HIBERNATE_TX_MANAGER, readOnly = true, isolation = READ_COMMITTED)
    @Override
    public long getData(long id) {
        return ((Number) sessionFactory.getCurrentSession()
                                       .createNativeQuery("select data from test where id = " + id)
                                       .uniqueResult()).longValue();
    }

    @Transactional(transactionManager = ADAPTIVE_HIBERNATE_TX_MANAGER, isolation = READ_COMMITTED)
    @Override
    public void update(long id, long data) {
        sessionFactory.getCurrentSession()
                      .createNativeQuery("update test set data = " + data + " where id = " + id)
                      .executeUpdate();
    }
}
