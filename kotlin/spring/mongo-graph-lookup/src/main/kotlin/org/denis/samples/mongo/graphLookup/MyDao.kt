package org.denis.samples.mongo.graphLookup

import com.mongodb.BasicDBObject
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.match
import org.springframework.data.mongodb.core.aggregation.GraphLookupOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.remove
import org.springframework.stereotype.Component

@Component
class MyDao(private val template: MongoTemplate) {

    fun lookupRecursively(ids: Set<String>): Collection<MyDocument> {
        val match = match(Criteria.where(FIELD_ID).`in`(ids))
        val aggregation = Aggregation.newAggregation(MyDocument::class.java,
                                                     match,
                                                     GraphLookupOperation
                                                             .builder()
                                                             .from(COLLECTION_NAME)
                                                             .startWith(FIELD_PARENTS)
                                                             .connectFrom(FIELD_PARENTS)
                                                             .connectTo(FIELD_ID)
                                                             .`as`(FIELD_HIERARCHY)
        )
        return template.aggregate(aggregation, MyDocument::class.java).mappedResults.flatMap {
            it.hierarchy ?: emptySet()
        }
    }

    fun store(documents: Collection<MyDocument>) {
        val bulkOps = template.bulkOps(BulkOperations.BulkMode.UNORDERED, MyDocument::class.java)
        documents.forEach {
            bulkOps.insert(it)
        }
        bulkOps.execute()
    }

    fun clear() {
        template.remove(Query(), COLLECTION_NAME)
    }
}