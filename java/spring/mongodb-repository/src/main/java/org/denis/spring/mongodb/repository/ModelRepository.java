package org.denis.spring.mongodb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ModelRepository extends CrudRepository<Model, String> {

    Model findByDbNameAndCollectionName(String dbName, String collectionName);
}
