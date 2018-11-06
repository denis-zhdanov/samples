package org.denis.spring.mongodb.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "my-model")
public class Model {

    private final String id;

    private final String dbName;
    private final String collectionName;
    private final String version;

    public Model(String dbName, String collectionName, String version) {
        this(null, dbName, collectionName, version);
    }

    @PersistenceConstructor
    public Model(String id, String dbName, String collectionName, String version) {
        this.id = id;
        this.dbName = dbName;
        this.collectionName = collectionName;
        this.version = version;
    }

    @Id
    public String getId() {
        return id;
    }

    public String getDbName() {
        return dbName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getVersion() {
        return version;
    }
}
