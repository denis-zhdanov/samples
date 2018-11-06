package org.denis.spring.mongodb.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelRepositoryTest {

    @Autowired ModelRepository repository;

    @Test
    public void test() {
        String dbName = "my-db";
        String collectionName = "my-collection";
        String version = "123";
        Model model = new Model(dbName, collectionName, version);
        repository.save(model);

        Model actual = repository.findByDbNameAndCollectionName(dbName, collectionName);
        assertThat(actual.getDbName()).isEqualTo(dbName);
        assertThat(actual.getCollectionName()).isEqualTo(collectionName);
        assertThat(actual.getVersion()).isEqualTo(version);
    }
}