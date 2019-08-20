package org.harmony.actuator

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Starter {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Starter::class.java)
            Thread.currentThread().join()
        }
    }
}