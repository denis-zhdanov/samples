package org.denis.sample.spring.test.mockbehavior;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.denis.sample.spring.test.mockbehavior.mock.DelegatingMockUtil.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class ServiceTest {

    @Autowired Service          service;
    @Autowired Map<String, Log> cache;

    @Test
    public void straightForward() {
        String id = "id1";
        service.store(new Log(id, "record1"));
        Log actual = service.store(new Log(id, singletonList("record2")));
        assertThat(actual.getRecords()).isEqualTo(asList("record1", "record2"));
    }

    @Test
    public void raceCondition() {
        String id = "id1";
        pushDelegate(cache, rewrite(cache, forMethod("get"), args -> {
            popDelegate(cache);
            service.store(new Log(id, "record1"));
            return null;
        }));
        Log actual = service.store(new Log(id, singletonList("record2")));
        assertThat(actual.getRecords()).isEqualTo(asList("record1", "record2"));
    }
}