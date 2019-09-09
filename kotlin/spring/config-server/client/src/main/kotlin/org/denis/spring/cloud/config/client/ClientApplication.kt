package org.denis.spring.cloud.config.client

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties
@SpringBootApplication
open class ClientApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ClientApplication::class.java)
            Thread.currentThread().join()
        }
    }
}