package org.denis.samples.mongo.graphLookup

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext


@Configuration
open class MyConfiguration {

    @Bean
    open fun mongoTemplate(dbFactory: MongoDbFactory): MongoTemplate {
        // Do not store _class column
        val converter = MappingMongoConverter(DefaultDbRefResolver(dbFactory), MongoMappingContext())
        converter.typeMapper = DefaultMongoTypeMapper(null)
        return MongoTemplate(dbFactory, converter)

    }
}