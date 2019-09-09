package org.denis.spring.cloud.config.client.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("environment")
class Environment {
    lateinit var server: Server
}

//@Component
//@ConfigurationProperties("server")
class Server {

    lateinit var address: String
    lateinit var login: String
    lateinit var password: String
}

//@Component
//@ConfigurationProperties("database")
data class Database(val address: String, val login: String, val password: String)