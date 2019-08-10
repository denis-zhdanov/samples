package org.denis.spring.micrometer

import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.autoconfigure.metrics.JvmMetricsAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.metrics.LogbackMetricsAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.metrics.web.servlet.WebMvcMetricsAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.metrics.web.tomcat.TomcatMetricsAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(exclude = [
    JvmMetricsAutoConfiguration::class, TomcatMetricsAutoConfiguration::class, LogbackMetricsAutoConfiguration::class,
    SystemMetricsAutoConfiguration::class, WebMvcMetricsAutoConfiguration::class
])
open class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java)
        }
    }
}