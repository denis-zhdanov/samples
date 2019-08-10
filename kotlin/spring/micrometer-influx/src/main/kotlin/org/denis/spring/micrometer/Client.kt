package org.denis.spring.micrometer

import org.springframework.web.client.RestTemplate
import java.time.Duration
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

val DURATION = Duration.ofSeconds(30)
val ACTIONS = listOf("first", "second", "third")

fun main() {
    val template = RestTemplate()
    val endTime = System.currentTimeMillis() + DURATION.toMillis()
    val latch = CountDownLatch(ACTIONS.size)
    for (action in ACTIONS) {
        thread {
            while (System.currentTimeMillis() < endTime) {
                template.getForEntity("http://127.0.0.1:8080/actions/$action", String::class.java)
            }
            latch.countDown()
        }
    }
    latch.await()
    println("The test is done")
}