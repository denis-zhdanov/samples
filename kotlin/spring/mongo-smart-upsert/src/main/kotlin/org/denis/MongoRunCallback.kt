package org.denis

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class MongoRunCallback(
    private val mongoTemplate: ReactiveMongoTemplate
): ApplicationRunner {

    private val properties = setOf(
        SampleDocument::payload,
        SampleDocument::version
    )

    override fun run(args: ApplicationArguments?) {
        val newState = SampleDocument("test-id", 4, "new-payload4")
        val condition = Criteria.where(SampleDocument::version.name).gte(newState.version)
        val update = AggregationUpdate.update()
        for (property in properties) {
            update.set(property.name).toValue(
                ConditionalOperators
                    .`when`(condition)
                    .thenValueOf(property.name)
                    .otherwise(property.get(newState))
            )
        }
        mongoTemplate.findAndModify(
            Query.query(Criteria.where("_id").`is`(newState.id)),
            update,
            FindAndModifyOptions.options().upsert(true),
            SampleDocument::class.java
        ).block()
    }
}