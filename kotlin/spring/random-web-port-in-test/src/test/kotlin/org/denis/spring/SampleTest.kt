package org.denis.spring

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SampleTest {

    private var port = 0

    @LocalServerPort
    fun setPort(port: Int) {
        this.port = port
    }

    @Test
    fun test() {
        assertThat(port).isNotEqualTo(0)
    }
}