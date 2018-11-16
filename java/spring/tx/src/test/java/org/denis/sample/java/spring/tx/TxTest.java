package org.denis.sample.java.spring.tx;

import org.denis.sample.java.spring.tx.service.AdaptiveHibernateService;
import org.denis.sample.java.spring.tx.service.BaseService;
import org.denis.sample.java.spring.tx.service.InitialHibernateService;
import org.denis.sample.java.spring.tx.service.InitialJdbcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class TxTest {

    @Autowired InitialJdbcService       initialServiceJdbc;
    @Autowired InitialHibernateService  initialServiceHibernate;
    @Autowired AdaptiveHibernateService adaptiveHibernateService;

    @Test
    public void whenDifferentJdbcTxManagers_uncommittedChangesAreNotVisible() {
        testInitialService(initialServiceJdbc);
    }

    @Test
    public void whenDifferentHibernateTxManagers_uncommittedChangesAreNotVisible() {
        testInitialService(initialServiceHibernate);
    }

    @Test
    public void whenAdaptiveHibernateTxManager_roTransactionEnrollsIntoRwTransaction() {
        testInitialService(adaptiveHibernateService);
    }

    private void testInitialService(BaseService service) {
        long id = System.currentTimeMillis();
        long value = 2;
        service.getDao().insert(id, value);
        long newValue = value + 2;
        long updated = service.update(id, newValue);
        assertThat(updated).isEqualTo(newValue);
    }
}
