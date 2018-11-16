package org.denis.sample.java.spring.tx.dao;

public interface Dao {

    void insert(long id, long data);

    long getData(long id);

    void update(long id, long data);
}
