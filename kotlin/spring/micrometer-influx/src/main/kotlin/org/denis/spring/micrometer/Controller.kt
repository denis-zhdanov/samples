package org.denis.spring.micrometer

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

@RestController
class Controller(private val registry: MeterRegistry) {

    private val timers = ConcurrentHashMap<String, Timer>()

    @RequestMapping("/actions/{action}")
    fun action(@PathVariable action: String) {
        timers.computeIfAbsent(action) {
            registry.timer("actions", "action", action)
        }.recordCallable {
            val duration = Random.nextLong(500)
            Thread.sleep(duration)
        }
    }
}