package org.denis.samples.mongo.graphLookup

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

const val COLLECTION_NAME = "doc"

const val FIELD_ID = "_id"
const val FIELD_PARENTS = "parents"
const val FIELD_HIERARCHY = "hierarchy"

@Document(collection = COLLECTION_NAME)
data class MyDocument @PersistenceConstructor constructor(
        @Id val id: String,
        @Field(FIELD_PARENTS) val parents: Set<String>? = null,
        @Field(FIELD_HIERARCHY) val hierarchy: Set<MyDocument>? = null
)