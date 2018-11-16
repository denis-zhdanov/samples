package org.denis.sample.java.spring.tx.service;

import org.denis.sample.java.spring.tx.dao.impl.AdaptiveHibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.denis.sample.java.spring.tx.di.DiConstants.ADAPTIVE_HIBERNATE_TX_MANAGER;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Component
public class AdaptiveHibernateService extends BaseService {

    @Autowired
    public AdaptiveHibernateService(AdaptiveHibernateDao dao) {
        super(dao);
    }

    @Transactional(transactionManager = ADAPTIVE_HIBERNATE_TX_MANAGER, isolation = READ_COMMITTED)
    @Override
    public long update(long id, long value) {
        return super.update(id, value);
    }
}
