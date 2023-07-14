package org.denis

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class SampleTest {

    @Autowired private lateinit var context: ApplicationContext

    @Test
    fun test() {
        val value = context.getMessage()
    }
}