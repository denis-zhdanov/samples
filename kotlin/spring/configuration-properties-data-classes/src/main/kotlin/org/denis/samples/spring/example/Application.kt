package org.denis.samples.spring.example

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Application(private val environment: Environment) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        println(environment)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
//            val mapper = ObjectMapper(YAMLFactory()).apply {
//                registerModule(KotlinModule())
//            }
//            val environment = mapper.readValue<Environment>("""
//                credentials:
//                  - login: login1
//                    password: password1
//                  - login: login2
//                    password: password2
//            """.trimIndent())
//            println(environment)
//            SpringApplication.run(Application::class.java)
        }
    }
}