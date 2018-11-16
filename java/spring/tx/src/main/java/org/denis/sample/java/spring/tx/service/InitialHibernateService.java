package org.denis.sample.java.spring.tx.service;

import org.denis.sample.java.spring.tx.dao.impl.InitialHibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.denis.sample.java.spring.tx.di.DiConstants.HIBERNATE_TX_MANAGER_RW;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Component
public class InitialHibernateService extends BaseService {

    @Autowired
    public InitialHibernateService(InitialHibernateDao dao) {
        super(dao);
    }

    @Transactional(transactionManager = HIBERNATE_TX_MANAGER_RW, isolation = READ_COMMITTED)
    @Override
    public long update(long id, long value) {
        return super.update(id, value);
    }
}
