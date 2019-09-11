package org.denis.spring.cloud.config.client.service

import org.denis.spring.cloud.config.client.model.Server
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyService(private val server: Server) {

    @PostConstruct
    fun onStart() {
        println("environment: $server")
    }
}