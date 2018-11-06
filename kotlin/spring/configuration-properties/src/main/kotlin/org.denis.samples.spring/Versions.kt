package org.denis.samples.spring

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("my.environment")
class Versions {

    var versions = mutableMapOf<String, String>()
}