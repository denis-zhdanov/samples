package org.denis.samples.spring

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.PropertySource

@PropertySource(value = ["classpath:my.yml"], factory = YamlPropertySourceFactory::class)
@SpringBootApplication
open class Application(private val versions: Versions) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        println(versions.versions)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java)
        }
    }
}