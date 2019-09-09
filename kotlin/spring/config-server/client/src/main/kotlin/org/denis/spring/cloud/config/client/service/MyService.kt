package org.denis.spring.cloud.config.client.service

import org.denis.spring.cloud.config.client.model.Environment
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyService(private val environment: Environment) {

    @PostConstruct
    fun onStart() {
        println("environment: $environment")
    }
}