package org.denis.sample.java.spring.tx.service;

import org.denis.sample.java.spring.tx.dao.Dao;
import org.denis.sample.java.spring.tx.dao.impl.InitialJdbcDao;
import org.springframework.transaction.annotation.Transactional;

import static org.denis.sample.java.spring.tx.di.DiConstants.HIBERNATE_TX_MANAGER_RW;
import static org.denis.sample.java.spring.tx.di.DiConstants.JDBC_TX_MANAGER_RW;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

public class BaseService {

    private final Dao dao;

    public BaseService(Dao dao) {
        this.dao = dao;
    }

    public Dao getDao() {
        return dao;
    }

    public long update(long id, long value) {
        dao.update(id, value);
        return dao.getData(id);
    }
}
