package org.denis.spring.mongodb.repository;

import com.github.fakemongo.Fongo;
import com.mongodb.MockMongoClient;
import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestConfiguration {

    @Bean
    public Fongo fongo() {
        return new Fongo("test");
    }

    @Bean
    public MongoClient mongoClient(Fongo fongo) {
        return MockMongoClient.create(fongo);
    }
}
