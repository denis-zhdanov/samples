package org.harmony.actuator.web.custom

data class Status(val up: Boolean, val details: List<StatusDetails>)

data class StatusDetails(val title: String, val error: String)