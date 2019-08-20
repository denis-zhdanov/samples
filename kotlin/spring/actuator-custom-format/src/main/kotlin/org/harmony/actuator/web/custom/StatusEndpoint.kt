package org.harmony.actuator.web.custom

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint
import org.springframework.stereotype.Component

@Component
@WebEndpoint(id = "status")
class StatusEndpoint {

    @get:ReadOperation
    val status: WebEndpointResponse<Status>
        get() {
            val status = Status(true,
                                listOf(StatusDetails("title1", ""),
                                       StatusDetails("title2", "")))
            return WebEndpointResponse(status)
        }
}