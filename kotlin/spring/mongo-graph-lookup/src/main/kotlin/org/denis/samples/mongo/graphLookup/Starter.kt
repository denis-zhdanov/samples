package org.denis.samples.mongo.graphLookup

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Starter {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val context = SpringApplication.run(Starter::class.java)

            val dao = context.getBean(MyDao::class.java)

            dao.clear()

            val grandFather1 = MyDocument("grandFather1")
            val grandMother1 = MyDocument("grandMother1")

            val grandFather2 = MyDocument("grandFather2")
            val grandMother2 = MyDocument("grandMother2")

            val father1 = MyDocument("father1", setOf("grandFather1", "grandMother1"))
            val mother1 = MyDocument("mother1", setOf("grandFather2", "grandMother2"))

            val child1 = MyDocument("child1", setOf("father1", "mother1"))

            dao.store(listOf(grandFather1, grandMother1, grandFather2, grandMother2, father1, mother1, child1))
            val lookupResult = dao.lookupRecursively(setOf("father1"))
            println("Found ${lookupResult.size} documents:")
            lookupResult.forEach {
                println("  $it")
            }
        }
    }
}