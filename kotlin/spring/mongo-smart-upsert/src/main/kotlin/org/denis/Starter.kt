package org.denis

import java.util.concurrent.TimeUnit
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing

@EnableReactiveMongoAuditing
@SpringBootApplication
open class Starter {

    companion object {

        private val LOGGER = LoggerFactory.getLogger(Starter::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Starter::class.java, *args)
        }
    }
}