package org.denis

import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

@Component
class MongoService(
    private val mongoTemplate: ReactiveMongoTemplate
) {

    fun createDocument(): SampleDocument {
        val id = UUID.randomUUID().toString()
        val now = LocalDateTime.now()
        return mongoTemplate.save(SampleDocument(id, 0, now, now)).block() as SampleDocument
    }

    fun updateDocumentViaFindAndModify(id: String, newVersion: Int): SampleDocument {
        return mongoTemplate.findAndModify(
            query(where("_id").`is`(id)),
            Update.update(SampleDocument::version.name, newVersion),
            SampleDocument::class.java
        ).block() as SampleDocument
    }
}