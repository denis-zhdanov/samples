package org.denis.sample.java.spring.tx.dao.impl;

import org.denis.sample.java.spring.tx.dao.Dao;
import org.denis.sample.java.spring.tx.di.DiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.denis.sample.java.spring.tx.di.DiConstants.JDBC_TX_MANAGER_RO;
import static org.denis.sample.java.spring.tx.di.DiConstants.JDBC_TX_MANAGER_RW;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@Component
public class InitialJdbcDao implements Dao {

    private final JdbcTemplate roTemplate;
    private final JdbcTemplate rwTemplate;

    @Autowired
    public InitialJdbcDao(@Qualifier(DiConstants.JDBC_TEMPLATE_RO) JdbcTemplate roTemplate,
                          @Qualifier(DiConstants.JDBC_TEMPLATE_RW) JdbcTemplate rwTemplate)
    {
        this.roTemplate = roTemplate;
        this.rwTemplate = rwTemplate;
    }

    public void insert(long id, long data) {
        rwTemplate.execute("insert into test (id, data) values (" +id +", " + data + ")");
    }

    @Transactional(transactionManager = JDBC_TX_MANAGER_RO, readOnly = true, isolation = READ_COMMITTED)
    public long getData(long id) {
        return roTemplate.queryForObject("select data from test where id = ?", Long.class, id);
    }

    @Transactional(transactionManager = JDBC_TX_MANAGER_RW, isolation = READ_COMMITTED)
    public void update(long id, long data) {
        rwTemplate.update("update test set data = ? where id = ?", data, id);
    };
}
