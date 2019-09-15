package org.denis.samples.spring.example

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties("environment")
@Component
data class Environment(val credentials: Collection<Credentials>)

data class Credentials(val login: String, val password: String)