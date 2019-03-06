package org.denis.samples.spring.metrics.jmx

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@RestController
class Controller(private val registry: MeterRegistry) {

    private val stats = ConcurrentHashMap<String, AtomicInteger>()

    @RequestMapping("/action/{action}")
    fun action(@PathVariable action: String) {
        stats.computeIfAbsent(action) {
            AtomicInteger().apply {
                registry.gauge(action, this) {
                    get().toDouble()
                }
            }
        }.incrementAndGet()
    }
}