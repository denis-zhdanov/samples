package org.denis.spring.cloud.config.client.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("server")
class Server {
    lateinit var address: String
}