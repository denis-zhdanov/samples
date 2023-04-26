package org.denis

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("data")
data class SampleDocument(
    @Id val id: String,
    val version: Int,
    val payload: String
)