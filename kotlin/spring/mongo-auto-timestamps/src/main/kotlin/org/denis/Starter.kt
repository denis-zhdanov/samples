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
            val context = SpringApplication.run(Starter::class.java, *args)
            val service = context.getBean(MongoService::class.java)
            var document = service.createDocument()
            LOGGER.info("created document: {}", document)
            sleep()
            document = service.updateDocumentViaFindAndModify(document.id, document.version + 1)
            LOGGER.info("updated document via findAndModify(): {}", document)
        }

        fun sleep() {
            val secondsToSleep = 2L
            LOGGER.info("sleeping {} seconds", secondsToSleep)
            Thread.sleep(TimeUnit.SECONDS.toMillis(secondsToSleep))
        }
    }
}